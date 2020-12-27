package com.example.microservice.orders.microservice.orders.dto.response;

import com.example.microservice.orders.microservice.orders.model.OrderStatus;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Integer id;
    private Integer customerId;
    private String description;
    private OrderStatus status;
}
