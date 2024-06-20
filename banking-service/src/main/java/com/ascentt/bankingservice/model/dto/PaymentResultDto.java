package com.ascentt.bankingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultDto {
    private boolean success;
    private String message;
    private String transactionId;
    private Double amount;
}
