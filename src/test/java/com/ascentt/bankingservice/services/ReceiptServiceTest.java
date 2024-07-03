package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.ReceiptDTO;
import com.ascentt.bankingservice.model.entities.Receipt;
import com.ascentt.bankingservice.repository.ReceiptRepository;
import com.ascentt.bankingservice.services.ReceiptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReceiptServiceTest {

    private ReceiptRepository receiptRepository;
    private ReceiptService receiptService;

    @BeforeEach
    public void setUp() {
        receiptRepository = mock(ReceiptRepository.class);
        receiptService = new ReceiptService(receiptRepository);
    }

    @Test
    public void testGenerateReceipt() {
        String transactionId = "12345";
        Double amount = 100.0;
        Receipt receipt = new Receipt(null, transactionId, amount, LocalDateTime.now());

        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);

        ReceiptDTO result = receiptService.generateReceipt(transactionId, amount);

        assertNotNull(result);
        assertEquals(transactionId, result.getTransactionId());
        assertEquals(amount, result.getAmount());
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }
}
