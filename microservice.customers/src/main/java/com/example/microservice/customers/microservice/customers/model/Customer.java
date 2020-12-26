package com.example.microservice.customers.microservice.customers.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Customer {

    @Id
    private Integer id;
    private String name;

}
