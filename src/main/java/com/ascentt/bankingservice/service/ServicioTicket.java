package com.ascentt.bankingservice.service;

import com.ascentt.bankingservice.model.Account;
import com.ascentt.bankingservice.model.Ticket;
import com.ascentt.bankingservice.repository.AccountRepository;
import com.ascentt.bankingservice.repository.TicketRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ServicioTicket {

    @Autowired
    TicketRepositorio ticketRepositorio;

    @Autowired
    AccountRepository accountRepository;

    public ResponseEntity<String> crearTicket( long idCuenta, Ticket ticket) {

        Account account= accountRepository.findById(idCuenta).get();
        account.getTickets().add(ticket);
        ticket.setCuenta(account);
        accountRepository.save(account);
        ticketRepositorio.save(ticket);
        return ResponseEntity.ok("Ticket creado correctamente");
    }
}
