package com.example.microservice.customers.microservice.customers.service;

import com.example.microservice.customers.microservice.customers.dto.CustomerDto;
import com.example.microservice.customers.microservice.customers.model.Customer;
import com.example.microservice.customers.microservice.customers.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public int saveCustomer(CustomerDto customerDto) {
        Customer customer = objectMapper.convertValue(customerDto, Customer.class);
        customerRepository.save(customer);
        return customer.getId();
    }
}
