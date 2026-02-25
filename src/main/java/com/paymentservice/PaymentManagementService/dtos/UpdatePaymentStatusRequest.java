package com.paymentservice.PaymentManagementService.dtos;

import com.paymentservice.PaymentManagementService.models.Payment.PaymentStatus;

import jakarta.validation.constraints.NotNull;

public class UpdatePaymentStatusRequest {

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus; // Only allow COMPLETED or FAILED

    // Getter & Setter
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
}