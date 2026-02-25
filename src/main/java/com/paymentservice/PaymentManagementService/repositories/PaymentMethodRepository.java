package com.paymentservice.PaymentManagementService.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymentservice.PaymentManagementService.models.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

	Page<PaymentMethod> findByIsActive(Boolean isActive, Pageable pageable);
}	