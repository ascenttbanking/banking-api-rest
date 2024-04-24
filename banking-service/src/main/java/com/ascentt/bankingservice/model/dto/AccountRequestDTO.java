package com.ascentt.bankingservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {
    @NotBlank(message = "El numero de cuenta no puede estar vacío")
    @Size(min=5, max=20, message= "El numero de cuenta debe tener entre 5 a 20 caracteres")
    @Pattern(regexp = "[0-9]+", message = "El numero de cuenta debe contener solo digitos")
    private String accountNumber;

    @NotNull(message = "El saldo no puede estar vacío")
    private BigDecimal balance;
    private String ownerName;
    @NotBlank(message = "El correo electrónico del titular no puede estar vacio")
    @Email
    private String ownerEmail;
}
