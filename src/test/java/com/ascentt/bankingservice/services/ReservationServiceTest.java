package com.ascentt.bankingservice.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ascentt.bankingservice.model.dto.ReservationDTO;
import com.ascentt.bankingservice.model.entities.Property;
import com.ascentt.bankingservice.model.entities.Reservation;
import com.ascentt.bankingservice.repository.PropertyRepository;
import com.ascentt.bankingservice.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateReservation() {
        Property property = new Property();
        property.setId(1L);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(1L);
        reservationDTO.setPropertyId(1L);
        reservationDTO.setStartDate(LocalDate.of(2025, 7, 21));
        reservationDTO.setEndDate(LocalDate.of(2025, 7, 25));
        reservationDTO.setStatus("CONFIRMED");

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation reservation = invocation.getArgument(0);
            reservation.setId(1L);
            return reservation;
        });

        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);

        assertNotNull(createdReservation.getId());
        assertNotNull(createdReservation.getConfirmationNumber());
        assertEquals(1L, createdReservation.getPropertyId());
        assertEquals(1L, createdReservation.getUserId());
    }

    @Test
    public void testCheckAvailability() {
        Property property1 = new Property();
        property1.setId(1L);

        Property property2 = new Property();
        property2.setId(2L);

        Reservation reservation = new Reservation();
        reservation.setProperty(property1);
        reservation.setStartDate(LocalDate.of(2025, 7, 20));
        reservation.setEndDate(LocalDate.of(2025, 7, 22));

        when(propertyRepository.findAll()).thenReturn(Arrays.asList(property1, property2));
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        List<Long> availableProperties = reservationService.checkAvailability(LocalDate.of(2025, 7, 23), LocalDate.of(2025, 7, 25));
        assertTrue(availableProperties.contains(1L));
        assertTrue(availableProperties.contains(2L));
    }

    @Test
    public void testCancelReservation() {
        doNothing().when(reservationRepository).deleteById(1L);

        reservationService.cancelReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }
}
