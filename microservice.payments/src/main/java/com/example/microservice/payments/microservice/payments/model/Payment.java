package com.example.microservice.payments.microservice.payments.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderId;
    private Long fullAmount;
    private Long paidAmount;
}
