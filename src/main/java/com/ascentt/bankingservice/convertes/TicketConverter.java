package com.ascentt.bankingservice.convertes;

import com.ascentt.bankingservice.model.dto.TicketDTO;
import com.ascentt.bankingservice.model.entities.Ticket;
import com.ascentt.bankingservice.model.entities.User;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

    public TicketDTO entityToDto(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setCosto(ticket.getCosto());
        dto.setFechaDeVenta(ticket.getFechaDeVenta());
        dto.setUsuarioId(ticket.getUsuario().getId());
        return dto;
    }

    public Ticket dtoToEntity(TicketDTO dto, User usuario) {
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setCosto(dto.getCosto());
        ticket.setFechaDeVenta(dto.getFechaDeVenta());
        ticket.setUsuario(usuario);
        return ticket;
    }
}
