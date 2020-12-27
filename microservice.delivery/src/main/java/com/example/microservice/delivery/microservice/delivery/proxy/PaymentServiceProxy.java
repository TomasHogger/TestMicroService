package com.example.microservice.delivery.microservice.delivery.proxy;

import com.example.microservice.delivery.microservice.delivery.dto.OrderDto;
import com.example.microservice.delivery.microservice.delivery.dto.request.UpdateOrderStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="orders", url = "localhost:8100")
public interface PaymentServiceProxy {
    @GetMapping("/get_order/{id}")
    OrderDto getOrderById(@PathVariable Integer id);

    @PutMapping("/update_order_status/")
    ResponseEntity<?> updateOrderStatus(@RequestBody UpdateOrderStatusDto updateOrderStatusDto);
}
