package com.ascentt.bankingservice.repository;

import com.ascentt.bankingservice.model.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("SELECT r FROM Receipt r WHERE r.paymentId = :paymentId")
    List<Receipt> findByPaymentId(@Param("paymentId") String paymentId);
}
