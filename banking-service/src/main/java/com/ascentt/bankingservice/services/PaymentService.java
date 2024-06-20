package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.convertes.PaymentConverter;
import com.ascentt.bankingservice.exceptions.PaymentProcessingException;
import com.ascentt.bankingservice.model.dto.PaymentDto;
import com.ascentt.bankingservice.model.dto.PaymentResultDto;
import com.ascentt.bankingservice.model.entities.Payment;
import com.ascentt.bankingservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentConverter paymentConverter;

    @Autowired
    private ReceiptService receiptService; // Inyección del servicio ReceiptService

    @Transactional
    public PaymentResultDto processPayment(PaymentDto paymentDto) {
        try {
            Payment payment = paymentConverter.dtoToEntity(paymentDto);
            payment.setStatus("PROCESSED");
            payment.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(payment);

            PaymentResultDto result = new PaymentResultDto();
            result.setSuccess(true);
            result.setMessage("Payment processed successfully");
            result.setTransactionId(payment.getTransactionId());
            result.setAmount(payment.getAmount());

            // Generar recibo automáticamente
            receiptService.generateReceipt(result);

            // Enviar notificación en tiempo real
            sendRealTimeNotification(result);

            return result;
        } catch (Exception e) {
            throw new PaymentProcessingException("Failed to process payment", e);
        }
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(paymentConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return paymentConverter.entityToDto(payment);
    }

    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existingPayment.setUserId(paymentDto.getUserId());
        existingPayment.setAmount(paymentDto.getAmount());
        existingPayment.setCurrency(paymentDto.getCurrency());
        existingPayment.setTransactionId(paymentDto.getTransactionId());
        existingPayment.setStatus(paymentDto.getStatus());
        existingPayment.setPaymentDate(paymentDto.getPaymentDate());

        paymentRepository.save(existingPayment);
        return paymentConverter.entityToDto(existingPayment);
    }

    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentRepository.delete(payment);
    }

    private void sendRealTimeNotification(PaymentResultDto result) {
        // Implementación para enviar la notificación en tiempo real
    }
}
