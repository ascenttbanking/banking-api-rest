package com.ascentt.bankingservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransactionRequestDTO {
        private Long id;


        @NotNull(message = "El numero de la cuenta de origen no puede estar vacio")
        private String sourceAccountNumber;

        @NotNull(message = "El numero de la cuenta de destino no puede estar vacio")
        private String targetAccountNumber;

        @NotNull(message = "La cantidad no puede estar vacía")
        @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a cero")
        private BigDecimal amount;

        @NotBlank(message = "La descripcion no puede estar vacia")
        private String description;
}
