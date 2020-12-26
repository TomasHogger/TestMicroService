package com.example.microservice.customers.microservice.customers.repository;

import com.example.microservice.customers.microservice.customers.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT id FROM Customer WHERE name = :name")
    List<Integer> findAllIdsByName(String name);
}
