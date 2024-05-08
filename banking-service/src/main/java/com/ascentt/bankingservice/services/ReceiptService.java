package com.ascentt.bankservice.services;

import com.ascentt.bankservice.model.dto.PaymentResultDto;
import com.ascentt.bankservice.model.entities.Receipt;
import com.ascentt.bankservice.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    public Receipt generateReceipt(PaymentResultDto paymentResult) {
        // Crea un nuevo recibo si el pago fue exitoso
        if (paymentResult.isSuccess()) {
            Receipt receipt = new Receipt();
            receipt.setPaymentId(Long.parseLong(paymentResult.getTransactionId())); // Asume que el Transaction ID es convertible a Long
            receipt.setContent(createReceiptContent(paymentResult)); // Supone una función para crear el contenido del recibo
            receipt.setCreatedDate(LocalDateTime.now()); // Fecha de creación del recibo
            return receiptRepository.save(receipt); // Guardar el recibo en la base de datos
        } else {
            throw new IllegalArgumentException("Payment failed, cannot generate receipt");
        }
    }

    private byte[] createReceiptContent(PaymentResultDto paymentResult) {

        String receiptText = "Receipt\nPayment ID: " + paymentResult.getTransactionId() +
                "\nAmount: " + paymentResult.getAmount() + // Asume que PaymentResultDto tiene un getter para Amount
                "\nStatus: " + paymentResult.getMessage(); // Mensaje del resultado del pago
        return receiptText.getBytes();
    }
}
