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
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Transactional
    public Reservation createReservation(ReservationDTO reservationDTO) {
        Property property = propertyRepository.findById(reservationDTO.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Reservation reservation = new Reservation();
        reservation.setProperty(property);
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());
        return reservationRepository.save(reservation);
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
}