package com.example.microservice.orders.microservice.orders.dto.request;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Integer customerId;
    private String description;
    private Long fullAmount;
}
