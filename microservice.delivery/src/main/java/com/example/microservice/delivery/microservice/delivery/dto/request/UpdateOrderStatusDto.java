package com.example.microservice.delivery.microservice.delivery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusDto {
    private Integer id;
    private OrderStatus status;
}
