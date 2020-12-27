package com.example.microservice.payments.microservice.payments.controller;

import com.example.microservice.payments.microservice.payments.dto.request.PayRequestDto;
import com.example.microservice.payments.microservice.payments.dto.request.PaymentRequestDto;
import com.example.microservice.payments.microservice.payments.dto.response.PayResponseDto;
import com.example.microservice.payments.microservice.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/pay/")
    public ResponseEntity<?> pay(@RequestBody PayRequestDto payRequestDto) {
        PayResponseDto payResponseDto = paymentService.pay(payRequestDto);
        return new ResponseEntity<>(payResponseDto, payResponseDto.isCorrectData() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete_payment/{orderId}")
    public ResponseEntity<?> deletePaymentByOrderId(@PathVariable Integer orderId) {
        return paymentService.deletePaymentByOrderId(orderId) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(HttpStatus.OK);
    }
}
