package com.example.microservice.payments.microservice.payments.controller;

import com.example.microservice.payments.microservice.payments.dto.request.PaymentRequestDto;
import com.example.microservice.payments.microservice.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/to_invoice/")
    public ResponseEntity<?> toInvoice(@RequestBody PaymentRequestDto paymentRequestDto) {
        Integer id = paymentService.createPayment(paymentRequestDto);
        return id == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(id, HttpStatus.OK);
    }

}
