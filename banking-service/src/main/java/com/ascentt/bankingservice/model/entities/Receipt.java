package com.ascentt.bankingservice.model.entities;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Base64;

@Data
@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Lob
    @Column(nullable = false)
    private byte[] content;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Transient
    private String contentBase64;

    @PostLoad
    @PostPersist
    @PostUpdate
    public void convertContentToBase64() {
        this.contentBase64 = Base64.getEncoder().encodeToString(this.content);
    }
}
