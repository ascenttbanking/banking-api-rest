package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.entities.Receipt;
import com.ascentt.bankingservice.repository.ReceiptRepository;
import com.ascentt.bankingservice.model.dto.PaymentResultDto;
import com.ascentt.bankingservice.exceptions.ReceiptGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Receipt generateReceipt(PaymentResultDto paymentResult) {
        if (!paymentResult.isSuccess()) {
            throw new ReceiptGenerationException("Cannot generate receipt for a failed payment");
        }

        try {
            // Convert paymentId to Long
            Long paymentId = Long.parseLong(paymentResult.getTransactionId());

            byte[] content = generateReceiptContent(paymentResult);
            LocalDateTime createdDate = LocalDateTime.now();

            // Save receipt manually using EntityManager
            entityManager.createNativeQuery("INSERT INTO receipt (payment_id, content, created_date) VALUES (?, ?, ?)")
                    .setParameter(1, paymentId)
                    .setParameter(2, content)
                    .setParameter(3, createdDate)
                    .executeUpdate();

            // Create and return the manually saved receipt
            Receipt receipt = new Receipt();
            receipt.setPaymentId(paymentId);
            receipt.setContent(content);
            receipt.setCreatedDate(createdDate);

            return receipt;
        } catch (Exception e) {
            throw new ReceiptGenerationException("Failed to generate receipt", e);
        }
    }

    private byte[] generateReceiptContent(PaymentResultDto paymentResult) {
        String receiptContent = "Receipt for Payment\n" +
                "Transaction ID: " + paymentResult.getTransactionId() + "\n" +
                "Amount: " + paymentResult.getAmount() + "\n" +
                "Date: " + LocalDateTime.now().toString() + "\n" +
                "Status: SUCCESS";
        return receiptContent.getBytes();
    }

    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    public Receipt getReceiptById(Long id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));
    }

    public List<Receipt> getReceiptsByPaymentId(String paymentId) {
        return receiptRepository.findByPaymentId(paymentId);
    }
}
