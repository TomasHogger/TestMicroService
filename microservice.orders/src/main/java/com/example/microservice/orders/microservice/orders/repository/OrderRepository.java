package com.example.microservice.orders.microservice.orders.repository;

import com.example.microservice.orders.microservice.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
