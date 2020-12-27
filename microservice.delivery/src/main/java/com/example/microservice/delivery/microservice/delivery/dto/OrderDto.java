package com.example.microservice.delivery.microservice.delivery.dto;

import com.example.microservice.delivery.microservice.delivery.dto.request.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {
    private Integer id;
    private Integer customerId;
    private String description;
    private OrderStatus status;
}
