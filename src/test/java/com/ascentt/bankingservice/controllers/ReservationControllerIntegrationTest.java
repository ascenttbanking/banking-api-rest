package com.ascentt.bankingservice.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ascentt.bankingservice.model.dto.ReservationDTO;
import com.ascentt.bankingservice.services.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationService reservationService;

    @Test
    public void testCreateReservation() throws Exception {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(1L);
        reservationDTO.setPropertyId(1L);
        reservationDTO.setStartDate(LocalDate.of(2025, 7, 21));
        reservationDTO.setEndDate(LocalDate.of(2025, 7, 25));
        reservationDTO.setStatus("CONFIRMED");

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.confirmationNumber").isNotEmpty());
    }

    @Test
    public void testGetReservationsByUser() throws Exception {
        // Primero, crea una reserva para el usuario con ID 1
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(1L);
        reservationDTO.setPropertyId(1L);
        reservationDTO.setStartDate(LocalDate.of(2025, 7, 21));
        reservationDTO.setEndDate(LocalDate.of(2025, 7, 25));
        reservationDTO.setStatus("CONFIRMED");
        reservationService.createReservation(reservationDTO);

        // Luego, realiza la solicitud para obtener las reservas del usuario
        mockMvc.perform(get("/reservations/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(1L));
    }

    @Test
    public void testUpdateReservation() throws Exception {
        // Primero, crea una reserva para el usuario con ID 1
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(1L);
        reservationDTO.setPropertyId(1L);
        reservationDTO.setStartDate(LocalDate.of(2025, 7, 21));
        reservationDTO.setEndDate(LocalDate.of(2025, 7, 25));
        reservationDTO.setStatus("CONFIRMED");
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);

        // Luego, realiza la solicitud para actualizar la reserva
        createdReservation.setStartDate(LocalDate.of(2025, 7, 22));
        createdReservation.setEndDate(LocalDate.of(2025, 7, 26));

        mockMvc.perform(put("/reservations/" + createdReservation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate").value("2025-07-22"))
                .andExpect(jsonPath("$.endDate").value("2025-07-26"));
    }

    @Test
    public void testCancelReservation() throws Exception {
        // Primero, crea una reserva para el usuario con ID 1
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(1L);
        reservationDTO.setPropertyId(1L);
        reservationDTO.setStartDate(LocalDate.of(2025, 7, 21));
        reservationDTO.setEndDate(LocalDate.of(2025, 7, 25));
        reservationDTO.setStatus("CONFIRMED");
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);

        // Luego, realiza la solicitud para cancelar la reserva
        mockMvc.perform(delete("/reservations/" + createdReservation.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCheckAvailability() throws Exception {
        // Primero, crea una reserva para bloquear una propiedad
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(1L);
        reservationDTO.setPropertyId(1L);
        reservationDTO.setStartDate(LocalDate.of(2025, 7, 21));
        reservationDTO.setEndDate(LocalDate.of(2025, 7, 25));
        reservationDTO.setStatus("CONFIRMED");
        reservationService.createReservation(reservationDTO);

        // Luego, realiza la solicitud para verificar la disponibilidad
        mockMvc.perform(get("/availability?startDate=2025-07-21&endDate=2025-07-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
