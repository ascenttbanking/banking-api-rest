package com.ascentt.bankingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;
    private double costo;
    private Date fechaDeVenta;
    private Long usuarioId;
}
