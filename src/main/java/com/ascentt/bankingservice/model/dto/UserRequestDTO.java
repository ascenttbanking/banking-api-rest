package com.ascentt.bankingservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "Email obligatorio")
    @Email(message = "Email debe ser válido")
    private String email;

    @NotBlank(message = "Contraseña obligatoria")
    private String password;

    @NotBlank(message = "Nombre de usuario obligatorio")
    private String username;
}
