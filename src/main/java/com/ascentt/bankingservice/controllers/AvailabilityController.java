package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.RoomAvailabilityDTO;
import com.ascentt.bankingservice.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Long>> checkAvailability(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Long> availableRooms = reservationService.checkAvailability(startDate, endDate);
        return ResponseEntity.ok(availableRooms);
    }
}