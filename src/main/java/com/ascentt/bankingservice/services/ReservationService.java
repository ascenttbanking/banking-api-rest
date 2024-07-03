package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.ReservationDTO;
import com.ascentt.bankingservice.model.entities.Property;
import com.ascentt.bankingservice.model.entities.Reservation;
import com.ascentt.bankingservice.repository.PropertyRepository;
import com.ascentt.bankingservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Property property = propertyRepository.findById(reservationDTO.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Reservation reservation = new Reservation();
        reservation.setProperty(property);
        reservation.setUserId(reservationDTO.getUserId()); // Asegurarse de que se asigne el userId
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());

        // Generar número de confirmación
        String confirmationNumber = UUID.randomUUID().toString();
        reservationDTO.setConfirmationNumber(confirmationNumber);
        reservation.setConfirmationNumber(confirmationNumber);

        // Guardar reserva en la base de datos
        Reservation savedReservation = reservationRepository.save(reservation);

        // Mapear los campos de Reservation a ReservationDTO y retornar
        reservationDTO.setId(savedReservation.getId());

        return reservationDTO;
    }

    public List<Long> checkAvailability(LocalDate startDate, LocalDate endDate) {
        List<Property> allProperties = propertyRepository.findAll();
        List<Reservation> reservations = reservationRepository.findAll();

        return allProperties.stream()
                .filter(property -> reservations.stream()
                        .noneMatch(reservation -> reservation.getProperty().getId().equals(property.getId()) &&
                                (startDate.isBefore(reservation.getEndDate()) && endDate.isAfter(reservation.getStartDate()))))
                .map(Property::getId)
                .collect(Collectors.toList());
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @Transactional
    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    // Listar todas las reservas activas para un usuario
    public List<ReservationDTO> getReservationsByUser(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return reservations.stream().map(reservation -> {
            ReservationDTO dto = new ReservationDTO();
            dto.setId(reservation.getId());
            dto.setUserId(userId);
            dto.setPropertyId(reservation.getProperty().getId());
            dto.setStartDate(reservation.getStartDate());
            dto.setEndDate(reservation.getEndDate());
            dto.setStatus(reservation.getStatus());
            dto.setConfirmationNumber(reservation.getConfirmationNumber());
            return dto;
        }).collect(Collectors.toList());
    }

    // Modificar una reserva existente
    @Transactional
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());
        reservationRepository.save(reservation);

        reservationDTO.setId(reservation.getId());
        return reservationDTO;
    }
}
