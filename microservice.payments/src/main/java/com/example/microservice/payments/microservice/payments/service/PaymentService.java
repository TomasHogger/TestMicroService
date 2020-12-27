package com.example.microservice.payments.microservice.payments.service;

import com.example.microservice.payments.microservice.payments.dto.request.OrderStatus;
import com.example.microservice.payments.microservice.payments.dto.request.PayRequestDto;
import com.example.microservice.payments.microservice.payments.dto.request.PaymentRequestDto;
import com.example.microservice.payments.microservice.payments.dto.request.UpdateOrderStatusDto;
import com.example.microservice.payments.microservice.payments.dto.response.PayResponseDto;
import com.example.microservice.payments.microservice.payments.model.Payment;
import com.example.microservice.payments.microservice.payments.proxy.OrderServiceProxy;
import com.example.microservice.payments.microservice.payments.repository.PaymentRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderServiceProxy orderServiceProxy;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderServiceProxy orderServiceProxy) {
        this.paymentRepository = paymentRepository;
        this.orderServiceProxy = orderServiceProxy;
    }

    public Integer createPayment(PaymentRequestDto paymentRequestDto) {
        if (paymentRequestDto.getOrderId() == null
                || paymentRequestDto.getFullAmount() == null
                || paymentRequestDto.getFullAmount() <= 0
                || paymentRepository.findById(paymentRequestDto.getOrderId()).isPresent()) {
            return null;
        }

        Payment payment = objectMapper.convertValue(paymentRequestDto, Payment.class);
        payment.setPaidAmount(0L);

        paymentRepository.save(payment);
        return payment.getId();
    }

    public PayResponseDto pay(PayRequestDto payRequestDto) {
        if (payRequestDto.getOrderId() == null
                || payRequestDto.getOrderId() < 0) {
            return new PayResponseDto(false, -1L, -1L, false);
        }

        Payment payment = paymentRepository.findByOrderId(payRequestDto.getOrderId());

        if (payment == null) {
            return new PayResponseDto(false, -1L, -1L, false);
        }

        boolean isFullyPaid = payment.getPaidAmount().equals(payment.getFullAmount());
        boolean isCorrectData = true;

        if (payRequestDto.getAmount() != null
                && payRequestDto.getAmount() > 0
                && !isFullyPaid
                && payment.getPaidAmount() + payRequestDto.getAmount() <= payment.getFullAmount()) {

            payment.setPaidAmount(payment.getPaidAmount() + payRequestDto.getAmount());

            if (payment.getPaidAmount().equals(payment.getFullAmount())) {
                orderServiceProxy.updateOrderStatus(new UpdateOrderStatusDto(payment.getId(), OrderStatus.PAID));
                isFullyPaid = true;
            }

            paymentRepository.save(payment);
        } else {
            isCorrectData = false;
        }
        return new PayResponseDto(isCorrectData, payment.getFullAmount(), payment.getPaidAmount(), isFullyPaid);
    }

    public boolean deletePaymentByOrderId(Integer orderId) {
        if (orderId == null || orderId < 0) {
            return false;
        }

        Payment payment = paymentRepository.findByOrderId(orderId);

        if (payment == null || payment.getPaidAmount() != 0) {
            return false;
        }

        paymentRepository.delete(payment);
        return true;
    }
}
