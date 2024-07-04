package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.TicketDTO;
import com.ascentt.bankingservice.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        TicketDTO createdTicket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.status(201).body(createdTicket);
    }

    @PutMapping("/edit")
    public ResponseEntity<TicketDTO> editTicket(@RequestBody TicketDTO ticketDTO) {
        TicketDTO updatedTicket = ticketService.editTicket(ticketDTO);
        if (updatedTicket != null) {
            return ResponseEntity.ok(updatedTicket);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
