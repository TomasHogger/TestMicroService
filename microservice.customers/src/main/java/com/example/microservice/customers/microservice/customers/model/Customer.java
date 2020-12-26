package com.example.microservice.customers.microservice.customers.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

}
