package com.example.microservice.orders.microservice.orders.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="customers", url = "localhost:8101")
public interface CustomerServiceProxy {
    @GetMapping("/get_customer/{id}")
    ResponseEntity<?> getCustomer(@PathVariable("id") Integer id);
}