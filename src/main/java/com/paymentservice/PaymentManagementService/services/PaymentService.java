package com.paymentservice.PaymentManagementService.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paymentservice.PaymentManagementService.dtos.CreatePaymentRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentStatusRequest;
import com.paymentservice.PaymentManagementService.models.Payment;

public interface PaymentService {
    Payment createPayment(CreatePaymentRequest request);
    Payment getPaymentById(Long id);
    Page<Payment> getPaymentsByUserId(Long userId, Pageable pageable);
    Page<Payment> getPaymentsByOrderId(Long orderId, Pageable pageable);
    Payment updatePayment(Long paymentId, UpdatePaymentRequest request);
    Payment updatePaymentStatus(Long paymentId, UpdatePaymentStatusRequest request);
}
