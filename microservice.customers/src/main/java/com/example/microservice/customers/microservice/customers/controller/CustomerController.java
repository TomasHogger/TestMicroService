package com.example.microservice.customers.microservice.customers.controller;

import com.example.microservice.customers.microservice.customers.dto.request.CustomerRequestDto;
import com.example.microservice.customers.microservice.customers.dto.response.CustomerResponseDto;
import com.example.microservice.customers.microservice.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get_customer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        CustomerResponseDto customer = customerService.getCustomerById(id);
        return (customer == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(customer, HttpStatus.OK));
    }

    @GetMapping("/get_customer_ids/{name}")
    public ResponseEntity<?> getCustomerIdsByName(@PathVariable String name) {
        List<Integer> customer = customerService.getCustomerIdsByName(name);
        return (customer == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(customer, HttpStatus.OK));
    }
}
