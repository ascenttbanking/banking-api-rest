package com.ascentt.bankservice.services;

import com.ascentt.bankservice.model.entities.Reservation;
import com.ascentt.bankservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public Reservation createReservation(Long roomId, LocalDate startDate, LocalDate endDate) {
        // Validaciones y creación de la reserva como ya está implementado
        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Las fechas de reserva no pueden estar en el pasado.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("La fecha de finalización debe ser después de la fecha de inicio.");
        }
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(roomId, startDate, endDate);
        if (!overlappingReservations.isEmpty()) {
            throw new IllegalStateException("La habitación ya está alquilada para las fechas proporcionadas.");
        }
        Reservation newReservation = new Reservation();
        newReservation.setRoomId(roomId);
        newReservation.setStartDate(startDate);
        newReservation.setEndDate(endDate);
        return reservationRepository.save(newReservation);
    }

    public List<Long> checkAvailability(LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.findReservationsBetweenDates(startDate, endDate);
        return reservations.stream()
                .map(Reservation::getRoomId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la reservación con el ID: " + reservationId));

        reservation.setStatus("Cancelled");  // Asegúrate de que la entidad Reservation tiene un campo status
        return reservationRepository.save(reservation);
    }
}
