package com.ascentt.bankingservice.repository;

import com.ascentt.bankingservice.model.entities.PropertyReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyReviewRepository extends JpaRepository<PropertyReview, Long> {
    List<PropertyReview> findByPropertyId(Long propertyId);
}
