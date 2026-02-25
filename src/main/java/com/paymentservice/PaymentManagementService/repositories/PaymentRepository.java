package com.paymentservice.PaymentManagementService.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymentservice.PaymentManagementService.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	 Page<Payment> findByUserId(Long userId, Pageable pageable);
	 Page<Payment> findByOrderId(Long orderId, Pageable pageable);
}