package com.ascentt.bankingservice.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.ascentt.bankingservice.services.StripeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    public static class CreateCheckoutSessionRequest {
        private String priceId;

        public String getPriceId() {
            return priceId;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }
    }

    @Operation(summary = "Crear una sesión de pago de Stripe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sesión de pago creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody CreateCheckoutSessionRequest request) throws StripeException {
        Map<String, String> response = stripeService.createCheckoutSession(request.getPriceId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener el estado de la sesión de Stripe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de la sesión obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/session-status")
    public ResponseEntity<Map<String, String>> getSessionStatus(@RequestParam String sessionId) throws StripeException {
        Session session = stripeService.retrieveSession(sessionId);
        Map<String, String> response = new HashMap<>();
        response.put("status", session.getStatus());
        response.put("customerEmail", session.getCustomerDetails() != null ? session.getCustomerDetails().getEmail() : "N/A");
        return ResponseEntity.ok(response);
    }
}
