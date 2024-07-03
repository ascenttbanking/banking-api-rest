package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.ReceiptDTO;
import com.ascentt.bankingservice.model.entities.Receipt;
import com.ascentt.bankingservice.repository.ReceiptRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReceiptRepository receiptRepository;

    @BeforeEach
    public void setUp() {
        receiptRepository.deleteAll();
    }

    @Test
    public void testProcessPayment() throws Exception {
        // Crear el request
        PaymentController.PaymentRequest paymentRequest = new PaymentController.PaymentRequest();
        paymentRequest.setTransactionId("12345");
        paymentRequest.setAmount(100.0);

        // Convertir el request a JSON
        String requestJson = objectMapper.writeValueAsString(paymentRequest);

        // Ejecutar la solicitud POST y verificar la respuesta
        mockMvc.perform(post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("12345"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }
}
