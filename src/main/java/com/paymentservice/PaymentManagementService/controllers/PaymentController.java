package com.paymentservice.PaymentManagementService.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservice.PaymentManagementService.dtos.CreatePaymentRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentStatusRequest;
import com.paymentservice.PaymentManagementService.models.Payment;
import com.paymentservice.PaymentManagementService.services.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        Payment payment = paymentService.createPayment(request);
        return ResponseEntity.ok(payment);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Payment>> getPaymentsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentsPage = paymentService.getPaymentsByUserId(userId, pageable);
        return ResponseEntity.ok(paymentsPage);
    }
    
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Page<Payment>> getPaymentsByOrderId(
            @PathVariable Long orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentsPage = paymentService.getPaymentsByOrderId(orderId, pageable);
        return ResponseEntity.ok(paymentsPage);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePaymentRequest request
    ) {
        Payment updatedPayment = paymentService.updatePayment(id, request);
        return ResponseEntity.ok(updatedPayment);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePaymentStatusRequest request
    ) {
        Payment updatedPayment = paymentService.updatePaymentStatus(id, request);
        return ResponseEntity.ok(updatedPayment);
    }
}