package com.example.microservice.customers.microservice.customers.controller;

import com.example.microservice.customers.microservice.customers.dto.CustomerDto;
import com.example.microservice.customers.microservice.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add_customer")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customerDto) {
        int id = customerService.saveCustomer(customerDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
