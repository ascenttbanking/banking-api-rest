package com.ascentt.bankingservice.repository;

import com.ascentt.bankingservice.model.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findByUserId(int userId);
}
