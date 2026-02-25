package com.paymentservice.PaymentManagementService.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservice.PaymentManagementService.dtos.ApiResponse;
import com.paymentservice.PaymentManagementService.dtos.CreatePaymentMethodRequest;
import com.paymentservice.PaymentManagementService.dtos.UpdatePaymentMethodRequest;
import com.paymentservice.PaymentManagementService.models.PaymentMethod;
import com.paymentservice.PaymentManagementService.services.PaymentMethodService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public ResponseEntity<Page<PaymentMethod>> getAllPaymentMethods(
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                                    : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods(isActive, pageable);
        return ResponseEntity.ok(paymentMethods);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable Long id) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
        return ResponseEntity.ok(paymentMethod);
    }
    
    @PostMapping
    public ResponseEntity<PaymentMethod> createPaymentMethod(
            @Valid @RequestBody CreatePaymentMethodRequest request) {

        PaymentMethod createdPaymentMethod = paymentMethodService.createPaymentMethod(request);
        return ResponseEntity.ok(createdPaymentMethod);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePaymentMethodRequest request) {

        PaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(id, request);
        return ResponseEntity.ok(updatedPaymentMethod);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        ApiResponse response = new ApiResponse(true, "Payment method deleted successfully with ID: " + id);
        return ResponseEntity.ok(response);
    }

}