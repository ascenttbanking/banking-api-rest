package com.ascentt.bankservice.repository;

import com.ascentt.bankservice.model.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Encuentra todos los pagos realizados por un usuario específico
    List<Payment> findByUserId(Long userId);

    // Encuentra pagos basados en su estado (e.g., completado, pendiente)
    List<Payment> findByStatus(String status);

    // Encuentra pagos dentro de un rango de fechas
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findAllByPaymentDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Encuentra un pago específico por su Transaction ID
    Payment findByTransactionId(String transactionId);
}
