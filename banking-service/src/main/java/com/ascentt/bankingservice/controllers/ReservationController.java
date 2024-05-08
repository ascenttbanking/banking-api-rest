package com.ascentt.bankservice.controllers;

import com.ascentt.bankservice.model.entities.Reservation;
import com.ascentt.bankservice.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestParam Long roomId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            Reservation reservation = reservationService.createReservation(roomId, start, end);
            return ResponseEntity.ok(reservation);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long reservationId) {
        try {
            Reservation reservation = reservationService.cancelReservation(reservationId);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error when trying to cancel reservation.");
        }
    }
}
