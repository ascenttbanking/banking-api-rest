package com.ascentt.bankingservice.repository;

import com.ascentt.bankingservice.model.entities.PropertyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyReviewRepository extends JpaRepository<PropertyReview, Long> {
    List<PropertyReview> findByPropertyId(Long propertyId);
}
