package com.paymentservice.PaymentManagementService.servicesimpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymentservice.PaymentManagementService.dtos.CreatePaymentMethodRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentMethodRequest;
import com.paymentservice.PaymentManagementService.exception.PaymentMethodException;
import com.paymentservice.PaymentManagementService.models.PaymentMethod;
import com.paymentservice.PaymentManagementService.repositories.PaymentMethodRepository;
import com.paymentservice.PaymentManagementService.services.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public Page<PaymentMethod> getAllPaymentMethods(Boolean isActive, Pageable pageable) {
        try {
            if (isActive != null) {
                return paymentMethodRepository.findByIsActive(isActive, pageable);
            } else {
                return paymentMethodRepository.findAll(pageable);
            }
        } catch (Exception e) {
            throw new PaymentMethodException("Failed to fetch payment methods", e);
        }
    }
    
    @Override
    public PaymentMethod getPaymentMethodById(Long id) {
        try {
            return paymentMethodRepository.findById(id)
                    .orElseThrow(() -> new PaymentMethodException("Payment method not found with ID: " + id));
        } catch (Exception e) {
            throw new PaymentMethodException("Failed to fetch payment method with ID: " + id, e);
        }
    }
    
    @Override
    public PaymentMethod createPaymentMethod(CreatePaymentMethodRequest request) {
        try {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setName(request.getName());
            paymentMethod.setType(request.getType());
            paymentMethod.setIsActive(request.getIsActive());

            return paymentMethodRepository.save(paymentMethod);
        } catch (Exception e) {
            throw new PaymentMethodException("Failed to create payment method", e);
        }
    }
    
    @Override
    public PaymentMethod updatePaymentMethod(Long id, UpdatePaymentMethodRequest request) {
        try {
            PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                    .orElseThrow(() -> new PaymentMethodException("Payment method not found with ID: " + id));

            paymentMethod.setName(request.getName());
            paymentMethod.setType(request.getType());
            paymentMethod.setIsActive(request.getIsActive());

            return paymentMethodRepository.save(paymentMethod);

        } catch (PaymentMethodException e) {
            throw e; // rethrow known exception
        } catch (Exception e) {
            throw new PaymentMethodException("Failed to update payment method with ID: " + id, e);
        }
    }
    
    @Override
    public void deletePaymentMethod(Long id) {
        try {
            if (!paymentMethodRepository.existsById(id)) {
                throw new PaymentMethodException("Payment method not found with ID: " + id);
            }
            paymentMethodRepository.deleteById(id);
        } catch (PaymentMethodException e) {
            throw e; // rethrow known exception
        } catch (Exception e) {
            throw new PaymentMethodException("Failed to delete payment method with ID: " + id, e);
        }
    }
}