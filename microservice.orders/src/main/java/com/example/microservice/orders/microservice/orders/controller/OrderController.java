package com.example.microservice.orders.microservice.orders.controller;

import com.example.microservice.orders.microservice.orders.dto.request.OrderRequestDto;
import com.example.microservice.orders.microservice.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete_order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        return orderService.deleteOrder(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
