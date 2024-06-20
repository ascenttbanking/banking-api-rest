package com.ascentt.bankingservice.convertes;

import com.ascentt.bankingservice.model.dto.PaymentDto;
import com.ascentt.bankingservice.model.entities.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PaymentDto entityToDto(Payment payment) {
        return modelMapper.map(payment, PaymentDto.class);
    }

    public Payment dtoToEntity(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, Payment.class);
    }

    public List<PaymentDto> entityToDto(List<Payment> payments) {
        return payments.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
