package com.ascentt.bankingservice.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequestDTO {
    @NotBlank(message = "Email obligatorio")
    @Email(message = "Email debe ser válido")
    private String email;

    @NotBlank(message = "Nueva contraseña obligatoria")
    private String newPassword;
}
