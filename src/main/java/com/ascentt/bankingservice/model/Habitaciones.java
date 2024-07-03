package com.ascentt.bankingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity(name = "Habitaciones")

public class Habitaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String nombre ;
    private String descripcion ;
    private double precio ;
    private boolean disponible ;
}
