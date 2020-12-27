package com.example.microservice.orders.microservice.orders.service;

import com.example.microservice.orders.microservice.orders.dto.request.OrderRequestDto;
import com.example.microservice.orders.microservice.orders.dto.request.OrderRequestWthIdDto;
import com.example.microservice.orders.microservice.orders.dto.request.PaymentRequestDto;
import com.example.microservice.orders.microservice.orders.dto.request.UpdateOrderStatusDto;
import com.example.microservice.orders.microservice.orders.dto.response.OrderResponseDto;
import com.example.microservice.orders.microservice.orders.model.Order;
import com.example.microservice.orders.microservice.orders.model.OrderStatus;
import com.example.microservice.orders.microservice.orders.proxy.CustomerServiceProxy;
import com.example.microservice.orders.microservice.orders.proxy.PaymentServiceProxy;
import com.example.microservice.orders.microservice.orders.repository.OrderRepository;
import com.example.microservice.orders.microservice.orders.utils.Validation;
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
            paymentServiceProxy.deletePaymentByOrderId(id);
            return true;
        }

        return false;
    }

    public boolean changeOrder(OrderRequestWthIdDto orderRequestWthIdDto) {
        if (!isOrderRequestWithIdDtoCorrect(orderRequestWthIdDto)) {
            return false;
        }

        Optional<Order> orderOptional = orderRepository.findById(orderRequestWthIdDto.getId());

        if (orderOptional.isEmpty() || orderOptional.get().getStatus() != OrderStatus.CREATED) {
            return false;
        }

        Order order = orderOptional.get();
        order.setDescription(orderRequestWthIdDto.getDescription());
        orderRepository.save(order);
        return true;
    }

    public boolean updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto) {
        if (!Validation.isPositiveOrZeroNumber(updateOrderStatusDto.getId())) {
            return false;
        }

        Optional<Order> orderOptional = orderRepository.findById(updateOrderStatusDto.getId());

        if (orderOptional.isEmpty()) {
            return false;
        }

        if (updateOrderStatusDto.getStatus() == orderOptional.get().getStatus()) {
            return false;
        }

        if (updateOrderStatusDto.getStatus() == OrderStatus.PART_PAID
                && orderOptional.get().getStatus() != OrderStatus.CREATED) {
            return false;
        }

        if (updateOrderStatusDto.getStatus() == OrderStatus.FULL_PAID
                && (orderOptional.get().getStatus() != OrderStatus.CREATED
                    && orderOptional.get().getStatus() != OrderStatus.PART_PAID)) {
            return false;
        }

        if (updateOrderStatusDto.getStatus() == OrderStatus.DELIVERED
                && orderOptional.get().getStatus() != OrderStatus.FULL_PAID) {
            return false;
        }

        Order order = orderOptional.get();
        order.setStatus(updateOrderStatusDto.getStatus());
        orderRepository.save(order);
        return true;
    }

    private boolean isOrderRequestWithIdDtoCorrect(OrderRequestWthIdDto orderRequestWthIdDto) {
        return Validation.isPositiveOrZeroNumber(orderRequestWthIdDto.getId())
                && Validation.isFullString(orderRequestWthIdDto.getDescription());
    }

    private boolean isOrderRequestDtoCorrect(OrderRequestDto orderRequestDto) {
        return Validation.isPositiveOrZeroNumber(orderRequestDto.getCustomerId())
                && Validation.isFullString(orderRequestDto.getDescription())
                && Validation.isPositiveNumber(orderRequestDto.getFullAmount());
    }

    private boolean isCustomerExists(int customerId) {
        try {
            customerServiceProxy.getCustomer(customerId);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }

    public OrderResponseDto getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(value -> objectMapper.convertValue(value, OrderResponseDto.class)).orElse(null);
    }
}
