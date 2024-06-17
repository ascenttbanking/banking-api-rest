package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.ReservationDTO;
import com.ascentt.bankingservice.model.entities.Reservation;
import com.ascentt.bankingservice.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservations")
@Validated
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}