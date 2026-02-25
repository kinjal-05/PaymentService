package com.paymentservice.PaymentManagementService.dtos;

import jakarta.validation.constraints.Size;

public class RefundRequest {

    @Size(max = 500, message = "Refund remarks cannot exceed 500 characters")
    private String remarks;

    // Getter & Setter
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}