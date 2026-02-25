package com.paymentservice.PaymentManagementService.dtos;

import com.paymentservice.PaymentManagementService.models.Payment.PaymentStatus;

import jakarta.validation.constraints.Size;

public class UpdatePaymentRequest {

    private PaymentStatus paymentStatus;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;

    // Getters and Setters
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}