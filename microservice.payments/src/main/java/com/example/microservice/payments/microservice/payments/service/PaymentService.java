package com.example.microservice.payments.microservice.payments.service;

import com.example.microservice.payments.microservice.payments.dto.request.PaymentRequestDto;
import com.example.microservice.payments.microservice.payments.model.Payment;
import com.example.microservice.payments.microservice.payments.repository.PaymentRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Integer createPayment(PaymentRequestDto paymentRequestDto) {
        if (paymentRequestDto.getOrderId() == null
                || paymentRequestDto.getFullAmount() == null
                || paymentRequestDto.getFullAmount() <= 0
                || paymentRepository.findById(paymentRequestDto.getOrderId()).isPresent()) {
            return null;
        }

        Payment payment = objectMapper.convertValue(paymentRequestDto, Payment.class);
        payment.setPaidAmount(0L);

        paymentRepository.save(payment);
        return payment.getId();
    }
}
