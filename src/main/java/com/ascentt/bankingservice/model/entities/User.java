package com.ascentt.bankingservice.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email obligatorio")
    @Email(message = "Email debe ser válido")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Contraseña obligatoria")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Nombre de usuario obligatorio")
    @Column(name = "username", nullable = false, unique = true)
    private String username;
}
