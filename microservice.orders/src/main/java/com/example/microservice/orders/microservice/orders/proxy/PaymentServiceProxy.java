package com.example.microservice.orders.microservice.orders.proxy;

import com.example.microservice.orders.microservice.orders.dto.request.PaymentRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="payments", url = "localhost:8102")
public interface PaymentServiceProxy {
    @PostMapping("/to_invoice/")
    ResponseEntity<?> toInvoice(@RequestBody PaymentRequestDto paymentRequestDto);
}
