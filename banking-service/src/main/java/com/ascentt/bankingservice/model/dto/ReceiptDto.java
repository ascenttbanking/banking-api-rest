package com.ascentt.bankingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {
    private Long id;
    private Long paymentId;
    private String content;
    private LocalDateTime createdDate;
}
