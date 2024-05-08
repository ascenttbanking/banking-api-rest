package com.ascentt.bankservice.controllers;

import com.ascentt.bankservice.model.dto.RoomAvailabilityDTO;
import com.ascentt.bankservice.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> checkRoomAvailability(@RequestBody RoomAvailabilityDTO availabilityDTO) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = availabilityDTO.getStartDate();
        LocalDate endDate = availabilityDTO.getEndDate();

        // Validaciones
        if (startDate == null || endDate == null) {
            return ResponseEntity.badRequest().body("Las fechas no pueden ser nulas.");
        }
        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        if (startDate.isBefore(today) || endDate.isBefore(today)) {
            return ResponseEntity.badRequest().body("Las fechas no pueden estar en el pasado.");
        }

        List<Long> availableRooms = reservationService.checkAvailability(startDate, endDate);
        return ResponseEntity.ok(availableRooms);
    }
}
