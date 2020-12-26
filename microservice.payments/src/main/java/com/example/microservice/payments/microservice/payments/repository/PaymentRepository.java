package com.example.microservice.payments.microservice.payments.repository;

import com.example.microservice.payments.microservice.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
