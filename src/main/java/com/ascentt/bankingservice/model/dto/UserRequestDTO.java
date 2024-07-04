package com.ascentt.bankingservice.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private Long id;

    @NotBlank(message = "Email obligatorio")
    @Email(message = "Email debe ser válido")
    private String email;

    @NotBlank(message = "Contraseña obligatoria")
    private String password;

    @NotBlank(message = "Nombre de usuario obligatorio")
    private String username;
}
