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

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private NotificationService notificationService;

    // Definición de PaymentRequest como una clase estática anidada
    public static class PaymentRequest {
        private String transactionId;
        private Double amount;
        private int userId;

        // Getters y Setters
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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
        ReceiptDTO receipt = receiptService.generateReceipt(request.getTransactionId(), request.getAmount(), request.getUserId());

        // Enviar notificación
        notificationService.sendNotification("user@example.com", "Su pago ha sido procesado con éxito. Transacción ID: " + request.getTransactionId());

        return ResponseEntity.ok(receipt);
    }

    @Operation(summary = "Obtener todos los pagos asociados con un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagos obtenidos exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/user/{userId}/payments")
    public ResponseEntity<List<ReceiptDTO>> getPaymentsByUserId(@PathVariable int userId) {
        List<ReceiptDTO> receipts = receiptService.getReceiptsByUserId(userId);
        if (receipts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receipts);
    }
}
