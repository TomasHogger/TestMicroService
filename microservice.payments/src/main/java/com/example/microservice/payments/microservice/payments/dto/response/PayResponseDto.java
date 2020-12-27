package com.example.microservice.payments.microservice.payments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayResponseDto {
    private boolean isCorrectData;
    private Long fullAmount;
    private Long paidAmount;
    private boolean isFullyPaid;
}
