package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Fine;
import com.example.ngBACKEND.Entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {

    List<Fine> findByTransaction(Transactions transaction);

    List<Fine> findByPaymentStatus(Fine.PaymentStatus paymentStatus);

    List<Fine> findByTransactionTransactionId(Integer transactionId);

    @Query("SELECT f FROM Fine f WHERE f.transaction.user.userId = :userId")
    List<Fine> findFinesByUser(@Param("userId") Integer userId);

    @Query("SELECT f FROM Fine f WHERE f.paymentStatus = 'PENDING'")
    List<Fine> findPendingFines();

    @Query("SELECT f FROM Fine f WHERE f.fineDate BETWEEN :startDate AND :endDate")
    List<Fine> findFinesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(f.fineAmount) FROM Fine f WHERE f.paymentStatus = 'PAID'")
    Optional<BigDecimal> getTotalPaidFines();

    @Query("SELECT SUM(f.fineAmount) FROM Fine f WHERE f.paymentStatus = 'PENDING'")
    Optional<java.math.BigDecimal> getTotalPendingFines();

    @Query("SELECT SUM(f.fineAmount) FROM Fine f WHERE f.transaction.user.userId = :userId AND f.paymentStatus = 'PENDING'")
    Optional<java.math.BigDecimal> getTotalPendingFinesByUser(@Param("userId") Integer userId);
}

