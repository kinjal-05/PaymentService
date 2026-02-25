package com.paymentservice.PaymentManagementService.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paymentservice.PaymentManagementService.dtos.RefundRequest;
import com.paymentservice.PaymentManagementService.models.Refund;

public interface RefundService {

    Refund initiateRefund(Long paymentId, RefundRequest request);
    Page<Refund> getAllRefunds(Pageable pageable);
    Refund getRefundById(Long id);
    Page<Refund> getRefundsByPaymentId(Long paymentId, Pageable pageable);
    Refund updateRefundStatus(Long refundId, String status);
}