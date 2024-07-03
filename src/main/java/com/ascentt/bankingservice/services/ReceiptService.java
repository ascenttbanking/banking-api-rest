package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.ReceiptDTO;
import com.ascentt.bankingservice.model.entities.Receipt;
import com.ascentt.bankingservice.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public ReceiptDTO generateReceipt(String transactionId, Double amount) {
        Receipt receipt = new Receipt(null, transactionId, amount, LocalDateTime.now());
        Receipt savedReceipt = receiptRepository.save(receipt);
        return new ReceiptDTO(savedReceipt.getId(), savedReceipt.getTransactionId(), savedReceipt.getAmount(), savedReceipt.getDate());
    }
}
