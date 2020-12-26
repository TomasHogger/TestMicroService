package com.example.microservice.customers.microservice.customers.controller;

import com.example.microservice.customers.microservice.customers.dto.request.CustomerRequestDto;
import com.example.microservice.customers.microservice.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add_customer")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        int id = customerService.saveCustomer(customerRequestDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
