package com.ascentt.bankingservice.repository;

import com.ascentt.bankingservice.model.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByPropertyIdAndStartDateBetweenOrEndDateBetween(Long propertyId, LocalDate startDate, LocalDate endDate, LocalDate startDate2, LocalDate endDate2);

    List<Reservation> findByStartDateBetweenOrEndDateBetween(LocalDate startDate, LocalDate endDate, LocalDate startDate2, LocalDate endDate2);
}
