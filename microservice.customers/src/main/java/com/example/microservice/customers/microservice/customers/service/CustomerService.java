package com.example.microservice.customers.microservice.customers.service;

import com.example.microservice.customers.microservice.customers.dto.request.CustomerRequestDto;
import com.example.microservice.customers.microservice.customers.dto.response.CustomerResponseDto;
import com.example.microservice.customers.microservice.customers.model.Customer;
import com.example.microservice.customers.microservice.customers.repository.CustomerRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public int saveCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = objectMapper.convertValue(customerRequestDto, Customer.class);
        customerRepository.save(customer);
        return customer.getId();
    }

    public CustomerResponseDto getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(value -> objectMapper.convertValue(value, CustomerResponseDto.class)).orElse(null);
    }

    public List<Integer> getCustomerIdsByName(String name) {
        List<Integer> ids = customerRepository.findAllIdsByName(name);
        return ids.isEmpty() ? null : ids;
    }
}
