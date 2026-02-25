package com.paymentservice.PaymentManagementService.servicesimpl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymentservice.PaymentManagementService.dtos.RefundRequest;
import com.paymentservice.PaymentManagementService.exception.ResourceNotFoundException;
import com.paymentservice.PaymentManagementService.models.Payment;
import com.paymentservice.PaymentManagementService.models.Refund;
import com.paymentservice.PaymentManagementService.repositories.PaymentRepository;
import com.paymentservice.PaymentManagementService.repositories.RefundRepository;
import com.paymentservice.PaymentManagementService.services.RefundService;

@Service
public class RefundServiceImpl implements RefundService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;

    public RefundServiceImpl(PaymentRepository paymentRepository, RefundRepository refundRepository) {
        this.paymentRepository = paymentRepository;
        this.refundRepository = refundRepository;
    }

    @Override
    public Refund initiateRefund(Long paymentId, RefundRequest request) {
        // 1️⃣ Validate payment existence
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));

        // 2️⃣ Only COMPLETED payments can be refunded
        if (payment.getPaymentStatus() != Payment.PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Only COMPLETED payments can be refunded");
        }

        // 3️⃣ Create Refund record
        Refund refund = new Refund();
        refund.setPayment(payment);
        refund.setAmount(payment.getAmount());
        refund.setStatus(Refund.RefundStatus.INITIATED);
        refund.setTransactionId("REF-" + UUID.randomUUID());
        refund.setRefundDate(LocalDateTime.now());
        refund.setRemarks(request != null ? request.getRemarks() : null);

        Refund savedRefund;
        try {
            savedRefund = refundRepository.save(refund);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create refund: " + e.getMessage(), e);
        }

        // 4️⃣ Update payment status to REFUNDED
        payment.setPaymentStatus(Payment.PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        // 5️⃣ Optionally call external refund API here

        return savedRefund;
    }
    
    @Override
    public Page<Refund> getAllRefunds(Pageable pageable) {
        Page<Refund> refunds = refundRepository.findAll(pageable);
        if (refunds.isEmpty()) {
            throw new ResourceNotFoundException("No refunds found");
        }
        return refunds;
    }
    
    @Override
    public Refund getRefundById(Long id) {
        return refundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refund with ID " + id + " not found"));
    }
    
    @Override
    public Page<Refund> getRefundsByPaymentId(Long paymentId, Pageable pageable) {
        // 1️⃣ Validate that payment exists
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));

        // 2️⃣ Fetch refunds by payment with pagination
        Page<Refund> refunds = refundRepository.findByPayment(payment, pageable);

        if (refunds.isEmpty()) {
            throw new ResourceNotFoundException("No refunds found for payment ID " + paymentId);
        }

        return refunds;
    }
    
    @Override
    public Refund updateRefundStatus(Long refundId, String status) {
        // 1️⃣ Find refund by ID
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new ResourceNotFoundException("Refund with ID " + refundId + " not found"));

        // 2️⃣ Update status based on input
        switch (status.toUpperCase()) {
            case "COMPLETED":
                refund.setStatus(Refund.RefundStatus.COMPLETED);
                break;
            case "FAILED":
                refund.setStatus(Refund.RefundStatus.FAILED);
                break;
            default:
                throw new IllegalArgumentException("Invalid refund status: " + status);
        }

        // 3️⃣ Save and return updated refund
        return refundRepository.save(refund);
    }
}