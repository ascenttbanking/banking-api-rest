package com.ascentt.bankingservice.model.entities;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(nullable = false)
    private String status;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;
}
