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

import com.paymentservice.PaymentManagementService.dtos.RefundRequest;
import com.paymentservice.PaymentManagementService.models.Refund;
import com.paymentservice.PaymentManagementService.services.RefundService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/refunds")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    // POST /payments/{id}/refund
    @PostMapping("/{id}/refund")
    public ResponseEntity<Refund> refundPayment(
            @PathVariable Long id,
            @Valid @RequestBody(required = false) RefundRequest request
    ) {
        Refund refund = refundService.initiateRefund(id, request);
        return ResponseEntity.ok(refund);
    }
    
    @GetMapping
    public ResponseEntity<Page<Refund>> getAllRefunds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Refund> refunds = refundService.getAllRefunds(pageable);
        return ResponseEntity.ok(refunds);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Refund> getRefundById(@PathVariable Long id) {
        Refund refund = refundService.getRefundById(id);
        return ResponseEntity.ok(refund);
    }
    
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<Page<Refund>> getRefundsByPayment(
            @PathVariable Long paymentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Refund> refunds = refundService.getRefundsByPaymentId(paymentId, pageable);
        return ResponseEntity.ok(refunds);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Refund> updateRefundStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        Refund updatedRefund = refundService.updateRefundStatus(id, status);
        return ResponseEntity.ok(updatedRefund);
    }
}