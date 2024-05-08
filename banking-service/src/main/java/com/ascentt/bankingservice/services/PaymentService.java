package com.ascentt.bankservice.services;

import com.ascentt.bankservice.model.dto.PaymentDetailsDto;
import com.ascentt.bankservice.model.dto.PaymentResultDto;
import com.ascentt.bankservice.model.entities.Payment;
import com.ascentt.bankservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public PaymentResultDto processPayment(PaymentDetailsDto paymentDetails) {
        Payment payment = new Payment();
        payment.setUserId(paymentDetails.getUserId());
        payment.setAmount(paymentDetails.getAmount());
        payment.setCurrency(paymentDetails.getCurrency());
        payment.setStatus("processed");  // Default status
        payment.setPaymentDate(LocalDateTime.now());

        payment = paymentRepository.save(payment);
        return convertToDto(payment);
    }

    private PaymentResultDto convertToDto(Payment payment) {
        PaymentResultDto paymentResultDto = new PaymentResultDto();
        paymentResultDto.setSuccess(true); // Assuming success for the example
        paymentResultDto.setMessage("Payment processed successfully");
        paymentResultDto.setTransactionId(String.valueOf(payment.getId())); // Example of generating a transaction ID
        return paymentResultDto;
    }

    @Transactional
    public PaymentDto processPayment(PaymentDto paymentDto) {
        try {
            Payment payment = PaymentConverter.dtoToEntity(paymentDto);
            // Aquí podrías incluir la lógica para interactuar con la pasarela de pagos o cualquier procesamiento necesario.
            payment = paymentRepository.save(payment);  // Guarda el pago en la base de datos.
            return PaymentConverter.entityToDto(payment);  // Convierte la entidad guardada de vuelta a DTO para la respuesta.
        } catch (Exception e) {
            throw new PaymentProcessingException("Failed to process payment", e);
        }
    }
}
