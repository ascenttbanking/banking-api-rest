package com.ascentt.bankingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private Long userId;
    private Double amount;
    private String currency;
    private String transactionId;
    private String status;
    private LocalDateTime paymentDate;
}
