package com.example.microservice.orders.microservice.orders.dto.request;

import com.example.microservice.orders.microservice.orders.model.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusDto {
    private Integer id;
    private OrderStatus status;
}
