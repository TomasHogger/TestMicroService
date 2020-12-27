package com.example.microservice.payments.microservice.payments.proxy;

import com.example.microservice.payments.microservice.payments.dto.request.UpdateOrderStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="orders", url = "localhost:8100")
public interface OrderServiceProxy {
    @PutMapping("/update_order_status/")
    ResponseEntity<?> updateOrderStatus(@RequestBody UpdateOrderStatusDto updateOrderStatusDto);
}