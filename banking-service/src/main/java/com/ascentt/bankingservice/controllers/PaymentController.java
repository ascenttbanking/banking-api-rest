package com.ascentt.bankservice.controllers;

import com.ascentt.bankservice.model.dto.PaymentDetailsDto;
import com.ascentt.bankservice.model.dto.PaymentResultDto;
import com.ascentt.bankservice.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResultDto> processPayment(@Valid @RequestBody PaymentDetailsDto paymentDetails) {
        logger.info("Procesando el pago para el usuario {}", paymentDetails.getUserId());
        try {
            PaymentResultDto result = paymentService.processPayment(paymentDetails);
            if (result.isSuccess()) {
                logger.info("Pago procesado correctamente para el ID de transacción{}", result.getTransactionId());
                return ResponseEntity.ok(result);
            } else {
                logger.warn("Procesamiento de pago fallido para el usuario {}: {}", paymentDetails.getUserId(), result.getMessage());
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception e) {
            logger.error("Error al procesar el pago del usuario {}: {}", paymentDetails.getUserId(), e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
