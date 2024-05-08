package com.ascentt.bankingservice.repository;

import com.ascentt.bankingservice.model.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Búsqueda por características de la propiedad
    List<Property> findByPriceLessThanEqual(double maxPrice);
    List<Property> findByRoomsGreaterThanEqual(int minRooms);
    List<Property> findByAddressContaining(String keyword);

    // Búsqueda por disponibilidad
    @Query("SELECT p FROM Property p WHERE p.id NOT IN (SELECT r.property.id FROM Reservation r WHERE r.startDate <= :endDate AND r.endDate >= :startDate)")
    List<Property> findAvailableProperties(LocalDate startDate, LocalDate endDate);

    // Actualizaciones personalizadas
    @Modifying
    @Transactional
    @Query("UPDATE Property p SET p.price = :price WHERE p.id = :id")
    int updatePropertyPriceById(Long id, double price);
}
