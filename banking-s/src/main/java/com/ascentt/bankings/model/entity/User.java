package com.ascentt.bankings.model.entity;

import jakarta.persistence.*;
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

    @Column(name="user_email", nullable = false, unique = true)
    private String email;

    @Column(name="user_password", nullable = false)
    private String password;

    @Column(name="username", nullable = false, unique = true)
    private String username;
}
