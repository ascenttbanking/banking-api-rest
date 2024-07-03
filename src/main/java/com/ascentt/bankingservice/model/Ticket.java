package com.ascentt.bankingservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@Entity(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double costo;
    private Date fechaDeVenta;
    @JsonIgnore
    @ManyToOne
    private Account cuenta;

}
