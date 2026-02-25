package com.paymentservice.PaymentManagementService.servicesimpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymentservice.PaymentManagementService.dtos.CreatePaymentRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentStatusRequest;
import com.paymentservice.PaymentManagementService.exception.ResourceNotFoundException;
import com.paymentservice.PaymentManagementService.models.Payment;
import com.paymentservice.PaymentManagementService.models.PaymentMethod;
import com.paymentservice.PaymentManagementService.repositories.PaymentMethodRepository;
import com.paymentservice.PaymentManagementService.repositories.PaymentRepository;
import com.paymentservice.PaymentManagementService.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,PaymentMethodRepository paymentMethodRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMethodRepository=paymentMethodRepository;
    }

    @Override
    public Payment createPayment(CreatePaymentRequest request) {
        try {
            // 1️⃣ Fetch the PaymentMethod entity from DB
        	PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
        		    .orElseThrow(() -> new RuntimeException("PaymentMethod not found"));


            // 2️⃣ Create the Payment object
            Payment payment = new Payment();
            payment.setOrderId(request.getOrderId());
            payment.setUserId(request.getUserId());
            payment.setAmount(request.getAmount());
            payment.setPaymentMethod(paymentMethod); // ✅ set the full entity
            payment.setPaymentStatus(request.getPaymentStatus());
            payment.setTransactionId(request.getTransactionId());
            payment.setPaymentDate(request.getPaymentDate());
            payment.setRemarks(request.getRemarks());

            // 3️⃣ Save to DB
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment", e);
        }
    }
    
    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment with ID " + id + " not found"));
    }
    
    @Override
    public Page<Payment> getPaymentsByUserId(Long userId, Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findByUserId(userId, pageable);

        if (paymentsPage.isEmpty()) {
            throw new ResourceNotFoundException("No payments found for user with ID " + userId);
        }

        return paymentsPage;
    }
    
    @Override
    public Page<Payment> getPaymentsByOrderId(Long orderId, Pageable pageable) {
        Page<Payment> paymentsPage = paymentRepository.findByOrderId(orderId, pageable);

        if (paymentsPage.isEmpty()) {
            throw new ResourceNotFoundException("No payments found for order with ID " + orderId);
        }

        return paymentsPage;
    }
    
    @Override
    public Payment updatePayment(Long paymentId, UpdatePaymentRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));

        // Only update fields if they are provided (partial update)
        if (request.getPaymentStatus() != null) {
            payment.setPaymentStatus(request.getPaymentStatus());
        }

        if (request.getRemarks() != null) {
            payment.setRemarks(request.getRemarks());
        }

        return paymentRepository.save(payment);
    }
    
    @Override
    public Payment updatePaymentStatus(Long paymentId, UpdatePaymentStatusRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));

        // Only allow COMPLETED or FAILED
        if (request.getPaymentStatus() != Payment.PaymentStatus.COMPLETED &&
            request.getPaymentStatus() != Payment.PaymentStatus.FAILED) {
            throw new IllegalArgumentException("Payment status must be COMPLETED or FAILED");
        }

        payment.setPaymentStatus(request.getPaymentStatus());
        return paymentRepository.save(payment);
    }

}