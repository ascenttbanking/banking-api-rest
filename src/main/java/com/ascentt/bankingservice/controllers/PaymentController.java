package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.ReceiptDTO;
import com.ascentt.bankingservice.services.NotificationService;
import com.ascentt.bankingservice.services.ReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private NotificationService notificationService;

    public static class PaymentRequest {
        private String transactionId;
        private Double amount;

        // Getters and Setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    @Operation(summary = "Procesar un pago y generar un recibo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago procesado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/process")
    public ResponseEntity<ReceiptDTO> processPayment(@RequestBody PaymentRequest request) {
        ReceiptDTO receipt = receiptService.generateReceipt(request.getTransactionId(), request.getAmount());

        // Enviar notificación
        notificationService.sendNotification("user@example.com", "Su pago ha sido procesado con éxito. Transacción ID: " + request.getTransactionId());

        return ResponseEntity.ok(receipt);
    }
}
