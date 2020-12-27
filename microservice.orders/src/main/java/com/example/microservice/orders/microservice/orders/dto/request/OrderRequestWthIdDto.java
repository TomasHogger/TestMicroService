package com.example.microservice.orders.microservice.orders.dto.request;

import lombok.Data;

@Data
public class OrderRequestWthIdDto {
    private Integer id;
    private String description;
}
