package com.paymentservice.PaymentManagementService.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paymentservice.PaymentManagementService.dtos.CreatePaymentMethodRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentMethodRequest;
import com.paymentservice.PaymentManagementService.models.PaymentMethod;

public interface PaymentMethodService {
	Page<PaymentMethod> getAllPaymentMethods(Boolean isActive, Pageable pageable);
	PaymentMethod getPaymentMethodById(Long id);
	PaymentMethod createPaymentMethod(CreatePaymentMethodRequest request);
	PaymentMethod updatePaymentMethod(Long id, UpdatePaymentMethodRequest request);
	void deletePaymentMethod(Long id);
}