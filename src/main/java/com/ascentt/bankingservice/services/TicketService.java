package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.TicketDTO;
import com.ascentt.bankingservice.model.entities.Ticket;
import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.repository.TicketRepository;
import com.ascentt.bankingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Optional<User> userOptional = userRepository.findById(ticketDTO.getUsuarioId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Ticket ticket = new Ticket();
            ticket.setCosto(ticketDTO.getCosto());
            ticket.setFechaDeVenta(ticketDTO.getFechaDeVenta());
            ticket.setUsuario(user);
            ticket = ticketRepository.save(ticket);
            return new TicketDTO(ticket.getId(), ticket.getCosto(), ticket.getFechaDeVenta(), ticket.getUsuario().getId());
        }
        return null;
    }

    public TicketDTO editTicket(TicketDTO ticketDTO) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketDTO.getId());
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setCosto(ticketDTO.getCosto());
            ticket.setFechaDeVenta(ticketDTO.getFechaDeVenta());
            ticketRepository.save(ticket);
            return new TicketDTO(ticket.getId(), ticket.getCosto(), ticket.getFechaDeVenta(), ticket.getUsuario().getId());
        }
        return null;
    }
}
