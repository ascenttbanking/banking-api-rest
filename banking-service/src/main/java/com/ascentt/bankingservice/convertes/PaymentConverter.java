package com.ascentt.bankservice.converters;

import com.ascentt.bankservice.model.dto.PaymentDto;
import com.ascentt.bankservice.model.entities.Payment;

public class PaymentConverter {

    public static PaymentDto entityToDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setUserId(payment.getUserId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setTransactionId(payment.getTransactionId());
        dto.setStatus(payment.getStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }

    public static Payment dtoToEntity(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setUserId(dto.getUserId());
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency());
        payment.setTransactionId(dto.getTransactionId());
        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(dto.getPaymentDate());
        return payment;
    }
}
