package com.example.microservice.delivery.microservice.delivery.controller;

import com.example.microservice.delivery.microservice.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/delivery/{orderId}")
    public ResponseEntity<?> delivery(@PathVariable Integer orderId) {
        boolean isDelivery = deliveryService.delivery(orderId);
        return new ResponseEntity<>(isDelivery ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
