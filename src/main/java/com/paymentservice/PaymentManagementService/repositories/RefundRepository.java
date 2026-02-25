package com.paymentservice.PaymentManagementService.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymentservice.PaymentManagementService.models.Payment;
import com.paymentservice.PaymentManagementService.models.Refund;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    // Optional: find all refunds for a specific payment
    List<Refund> findByPayment(Payment payment);
    Page<Refund> findByPayment(Payment payment, Pageable pageable);
}