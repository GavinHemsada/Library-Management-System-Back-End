package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.Entity.Fine;
import com.example.ngBACKEND.Entity.Transactions;
import com.example.ngBACKEND.Repostry.FineRepository;
import com.example.ngBACKEND.Repostry.TransactionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;
    @Cacheable(value = "allFines")
    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    public Optional<Fine> getFineById(Integer id) {
        return fineRepository.findById(id);
    }
    @Cacheable(value = "userAllFines", key = "#userId")
    public List<Fine> getFinesByUser(Integer userId) {
        return fineRepository.findFinesByUser(userId);
    }

    public List<Fine> getFinesByTransaction(Integer transactionId) {
        return fineRepository.findByTransactionTransactionId(transactionId);
    }

    public List<Fine> getPendingFines() {
        return fineRepository.findPendingFines();
    }

    public List<Fine> getFinesByPaymentStatus(Fine.PaymentStatus paymentStatus) {
        return fineRepository.findByPaymentStatus(paymentStatus);
    }

    public List<Fine> getFinesByDateRange(LocalDate startDate, LocalDate endDate) {
        return fineRepository.findFinesByDateRange(startDate, endDate);
    }
    @CacheEvict(value = {"allFines", "userAllFines"}, allEntries = true)
    public Fine payFine(Integer fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new RuntimeException("Fine not found with id: " + fineId));

        if (fine.getPaymentStatus() == Fine.PaymentStatus.PAID) {
            throw new RuntimeException("Fine is already paid");
        }

        fine.setPaymentStatus(Fine.PaymentStatus.PAID);
        fine.setPaymentDate(LocalDate.now());
        fine.setUpdatedAt(LocalDateTime.now());

        return fineRepository.save(fine);
    }

    public BigDecimal getTotalPaidFines() {
        return fineRepository.getTotalPaidFines().orElse(BigDecimal.ZERO);
    }

    public BigDecimal getTotalPendingFines() {
        return fineRepository.getTotalPendingFines().orElse(BigDecimal.ZERO);
    }

    public BigDecimal getTotalPendingFinesByUser(Integer userId) {
        return fineRepository.getTotalPendingFinesByUser(userId).orElse(BigDecimal.ZERO);
    }

    public Fine createFine(Integer transactionId, BigDecimal amount, String reason) {
        Transactions transaction = transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));

        Fine fine = new Fine();
        fine.setTransaction(transaction);
        fine.setFineAmount(amount);
        fine.setFineDate(LocalDate.now());
        fine.setPaymentStatus(Fine.PaymentStatus.PENDING);
        fine.setReason(reason);
        fine.setCreatedAt(LocalDateTime.now());
        fine.setUpdatedAt(LocalDateTime.now());

        return fineRepository.save(fine);

    }
    @CacheEvict(value = {"allFines" ,"userAllFines"}, allEntries = true)
    public String waiveFine(Integer fineId, String reason) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new RuntimeException("Fine not found with id: " + fineId));

        fine.setPaymentStatus(Fine.PaymentStatus.WAIVED);
        fine.setPaymentDate(LocalDate.now());
        fine.setReason(fine.getReason() + " - WAIVED: " + reason);
        fine.setUpdatedAt(LocalDateTime.now());

        fineRepository.save(fine);
        return "Successful WaiveFine";
    }

}