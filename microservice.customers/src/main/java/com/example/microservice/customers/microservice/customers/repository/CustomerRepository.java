package com.example.microservice.customers.microservice.customers.repository;

import com.example.microservice.customers.microservice.customers.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
