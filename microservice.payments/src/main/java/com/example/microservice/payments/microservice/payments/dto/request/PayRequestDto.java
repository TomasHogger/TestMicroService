package com.example.microservice.payments.microservice.payments.dto.request;

import lombok.Data;

@Data
public class PayRequestDto {
    private Integer orderId;
    private Long amount;
}
