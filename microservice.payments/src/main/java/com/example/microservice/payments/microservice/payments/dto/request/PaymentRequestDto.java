package com.example.microservice.payments.microservice.payments.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private Integer orderId;
    private Long fullAmount;
}
