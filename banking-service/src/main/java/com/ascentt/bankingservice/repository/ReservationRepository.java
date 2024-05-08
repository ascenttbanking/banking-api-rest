package com.ascentt.bankservice.repository;

import com.ascentt.bankservice.model.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.roomId = :roomId AND NOT (r.startDate > :endDate OR r.endDate < :startDate)")
    List<Reservation> findOverlappingReservations(Long roomId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Reservation r WHERE NOT (r.startDate > :endDate OR r.endDate < :startDate)")
    List<Reservation> findReservationsBetweenDates(LocalDate startDate, LocalDate endDate);
}
