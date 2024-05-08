import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;


public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    //Buscar recibos por rango de fechas
    @Query("SELECT r FROM Receipt r WHERE r.createdDate BETWEEN :startDate AND :endDate")
    List<Receipt> findByCreatedDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //Buscar el último recibo para un usuario o pago
    @Query("SELECT r FROM Receipt r WHERE r.paymentId = :paymentId ORDER BY r.createdDate DESC")
    List<Receipt> findLatestByPaymentId(@Param("paymentId") Long paymentId);
}

