package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.entities.Receipt;
import com.ascentt.bankingservice.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @GetMapping
    public List<Receipt> getAllReceipts() {
        List<Receipt> receipts = receiptService.getAllReceipts();
        receipts.forEach(Receipt::convertContentToBase64);
        return receipts;
    }

    @GetMapping("/payment/{paymentId}")
    public List<Receipt> getReceiptsByPaymentId(@PathVariable String paymentId) {
        return receiptService.getReceiptsByPaymentId(paymentId);
    }
}
