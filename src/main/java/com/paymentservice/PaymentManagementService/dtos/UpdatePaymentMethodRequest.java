package com.paymentservice.PaymentManagementService.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdatePaymentMethodRequest {

    @NotBlank(message = "Payment method name is required")
    private String name;

    @Size(max = 50, message = "Type cannot exceed 50 characters")
    private String type; // optional

    @NotNull(message = "isActive flag must be provided")
    private Boolean isActive;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}