package com.example.microservice.orders.microservice.orders.service;

import com.example.microservice.orders.microservice.orders.dto.request.OrderRequestDto;
import com.example.microservice.orders.microservice.orders.dto.request.PaymentRequestDto;
import com.example.microservice.orders.microservice.orders.model.Order;
import com.example.microservice.orders.microservice.orders.model.OrderStatus;
import com.example.microservice.orders.microservice.orders.proxy.CustomerServiceProxy;
import com.example.microservice.orders.microservice.orders.proxy.PaymentServiceProxy;
import com.example.microservice.orders.microservice.orders.repository.OrderRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerServiceProxy customerServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerServiceProxy customerServiceProxy, PaymentServiceProxy paymentServiceProxy) {
        this.orderRepository = orderRepository;
        this.customerServiceProxy = customerServiceProxy;
        this.paymentServiceProxy = paymentServiceProxy;
    }

    public Integer addOrder(OrderRequestDto orderRequestDto) {
        if (!isOrderRequestDtoCorrect(orderRequestDto)
                || !isCustomerExists(orderRequestDto.getCustomerId())) {
            return null;
        }

        Order order = objectMapper.convertValue(orderRequestDto, Order.class);
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        paymentServiceProxy.toInvoice(new PaymentRequestDto(
                order.getId(),
                orderRequestDto.getFullAmount()));

        return order.getId();
    }

    public boolean deleteOrder(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent() && order.get().getStatus() == OrderStatus.CREATED) {
            orderRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private boolean isOrderRequestDtoCorrect(OrderRequestDto orderRequestDto) {
        return orderRequestDto.getCustomerId() != null
                && orderRequestDto.getCustomerId() >= 0
                && orderRequestDto.getDescription() != null
                && !orderRequestDto.getDescription().isEmpty()
                && orderRequestDto.getFullAmount() != null
                && orderRequestDto.getFullAmount() > 0;
    }

    private boolean isCustomerExists(int customerId) {
        try {
            customerServiceProxy.getCustomer(customerId);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }
}
