package com.example.microservice.orders.microservice.orders.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer customerId;
    private String description;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
