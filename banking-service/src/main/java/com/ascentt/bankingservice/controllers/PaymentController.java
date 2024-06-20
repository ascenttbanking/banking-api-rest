package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.PaymentDto;
import com.ascentt.bankingservice.model.dto.PaymentResultDto;
import com.ascentt.bankingservice.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    public PaymentController() {
        logger.info("PaymentController instantiated");
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResultDto> processPayment(@RequestBody PaymentDto paymentDto) {
        try {
            logger.info("Processing payment: {}", paymentDto);
            PaymentResultDto result = paymentService.processPayment(paymentDto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error processing payment: {}", e.getMessage());
            return ResponseEntity.status(500).body(new PaymentResultDto(false, "Payment processing failed", null, 0.0));
        }
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        logger.info("Fetching all payments");
        List<PaymentDto> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        try {
            logger.info("Fetching payment with id: {}", id);
            PaymentDto payment = paymentService.getPaymentById(id);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            logger.error("Payment not found: {}", e.getMessage());
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto) {
        try {
            logger.info("Updating payment with id: {}", id);
            PaymentDto updatedPayment = paymentService.updatePayment(id, paymentDto);
            return ResponseEntity.ok(updatedPayment);
        } catch (RuntimeException e) {
            logger.error("Error updating payment: {}", e.getMessage());
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        try {
            logger.info("Deleting payment with id: {}", id);
            paymentService.deletePayment(id);
            return ResponseEntity.ok("{\"message\": \"Payment deleted successfully\"}");
        } catch (RuntimeException e) {
            logger.error("Error deleting payment: {}", e.getMessage());
            return ResponseEntity.status(500).body("{\"message\": \"Error deleting payment\"}");
        }
    }
}
