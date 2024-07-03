package com.ascentt.bankingservice.controller;

import com.ascentt.bankingservice.model.Account;
import com.ascentt.bankingservice.model.Ticket;
import com.ascentt.bankingservice.service.AccountService;
import com.ascentt.bankingservice.service.ServicioTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tick")
public class ControladorTicket {

    @Autowired
    private ServicioTicket servicioTicket;


    @PostMapping("/crearTicket/{idCuenta}")
    public ResponseEntity<String> createAccount(@PathVariable long idCuenta, @RequestBody Ticket ticket) {
    return servicioTicket.crearTicket(idCuenta,ticket);
    }

}
