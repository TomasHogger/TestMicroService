package com.example.microservice.delivery.microservice.delivery.proxy;

import com.example.microservice.delivery.microservice.delivery.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="customers", url = "localhost:8101")
public interface CustomerServiceProxy {
    @GetMapping("/get_customer/{id}")
    CustomerDto getCustomer(@PathVariable("id") Integer id);
}