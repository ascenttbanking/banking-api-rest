package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.ReceiptDTO;
import com.ascentt.bankingservice.model.entities.Receipt;
import com.ascentt.bankingservice.repository.ReceiptRepository;
import com.ascentt.bankingservice.services.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReceiptServiceTest {

    private ReceiptRepository receiptRepository;
    private ReceiptService receiptService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        receiptRepository = mock(ReceiptRepository.class);
        modelMapper = new ModelMapper();
        receiptService = new ReceiptService(receiptRepository, modelMapper);
    }

    @Test
    public void testGenerateReceipt() {
        String transactionId = "12345";
        Double amount = 100.0;
        int userId = 1;
        Receipt receipt = new Receipt(null, transactionId, amount, LocalDateTime.now(), userId);

        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);

        ReceiptDTO result = receiptService.generateReceipt(transactionId, amount, userId);

        assertNotNull(result);
        assertEquals(transactionId, result.getTransactionId());
        assertEquals(amount, result.getAmount());
        assertEquals(userId, result.getUserId());
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    public void testGetReceiptsByUserId() {
        int userId = 1;
        Receipt receipt1 = new Receipt(1L, "12345", 100.0, LocalDateTime.now(), userId);
        Receipt receipt2 = new Receipt(2L, "12346", 200.0, LocalDateTime.now(), userId);

        when(receiptRepository.findByUserId(userId)).thenReturn(Arrays.asList(receipt1, receipt2));

        List<ReceiptDTO> result = receiptService.getReceiptsByUserId(userId);

        assertEquals(2, result.size());
        verify(receiptRepository, times(1)).findByUserId(userId);
    }
}
