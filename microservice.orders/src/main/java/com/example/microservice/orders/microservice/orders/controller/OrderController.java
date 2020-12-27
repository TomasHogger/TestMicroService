package com.example.microservice.orders.microservice.orders.controller;

import com.example.microservice.orders.microservice.orders.dto.request.OrderRequestDto;
import com.example.microservice.orders.microservice.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add_order/")
    public ResponseEntity<?> addOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Integer id = orderService.addOrder(orderRequestDto);
        return id == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(id, HttpStatus.OK);
    }

}
