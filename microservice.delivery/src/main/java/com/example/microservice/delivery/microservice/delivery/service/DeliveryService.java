package com.example.microservice.delivery.microservice.delivery.service;

import com.example.microservice.delivery.microservice.delivery.dto.CustomerDto;
import com.example.microservice.delivery.microservice.delivery.dto.OrderDto;
import com.example.microservice.delivery.microservice.delivery.dto.request.OrderStatus;
import com.example.microservice.delivery.microservice.delivery.dto.request.UpdateOrderStatusDto;
import com.example.microservice.delivery.microservice.delivery.proxy.CustomerServiceProxy;
import com.example.microservice.delivery.microservice.delivery.proxy.PaymentServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final CustomerServiceProxy customerServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;

    @Autowired
    public DeliveryService(CustomerServiceProxy customerServiceProxy, PaymentServiceProxy paymentServiceProxy) {
        this.customerServiceProxy = customerServiceProxy;
        this.paymentServiceProxy = paymentServiceProxy;
    }

    public boolean delivery(Integer orderId) {
        if (orderId == null || orderId < 0) {
            return false;
        }

        OrderDto orderDto;
        try {
            orderDto = paymentServiceProxy.getOrderById(orderId);
        } catch (Exception e) {
            return false;
        }

        if (orderDto.getStatus() != OrderStatus.FULL_PAID) {
            return false;
        }

        paymentServiceProxy.updateOrderStatus(new UpdateOrderStatusDto(orderDto.getId(), OrderStatus.DELIVERED));

        CustomerDto customerDto = customerServiceProxy.getCustomer(orderDto.getCustomerId());

        //TODO отправка email

        return true;
    }
}
