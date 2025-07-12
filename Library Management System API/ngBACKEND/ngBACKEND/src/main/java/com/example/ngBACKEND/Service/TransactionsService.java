package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.TransactionDTO;
import com.example.ngBACKEND.Entity.*;
import com.example.ngBACKEND.Repostry.*;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private ReservationRepository reservationsRepository;
    @Cacheable(value = "allTransactions")
    public List<TransactionDTO> getAllTransactions() {
        return transactionsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDTO> getTransactionById(Integer id) {
        return transactionsRepository.findById(id).map(this::convertToDTO);
    }

    @Cacheable(value = "userAllTransactions",key = "#userId")
    public List<TransactionDTO> getTransactionsByUser(Integer userId) {
        return transactionsRepository.findByUserUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByBook(Integer bookId) {
        return transactionsRepository.findByBookBookId(bookId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByStatus(Transactions.TransactionStatus status) {
        return transactionsRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getOverdueTransactions() {
        return transactionsRepository.findOverdueTransactions(LocalDate.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Transactions> getActiveTransactionsByUser(Integer userId) {
        return transactionsRepository.findActiveTransactionsByUser(userId);
    }

    public List<TransactionDTO> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionsRepository.findTransactionsByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO issueBook(Integer userId, Integer bookId, Integer daysToReturn, String note) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        // Check if book is available
        if (book.getCopiesAvailable() <= 0) {
            throw new RuntimeException("Book is not available for issue");
        }

        // Check if user has pending fines
        BigDecimal pendingFines = fineRepository.getTotalPendingFinesByUser(userId).orElse(BigDecimal.ZERO);
        if (pendingFines.compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("User has pending fines. Please clear fines before issuing books");
        }

        // Create transaction
        Transactions transaction = new Transactions();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(daysToReturn));
        transaction.setStatus(Transactions.TransactionStatus.ISSUED);
        transaction.setNotes(note);
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setCreatedAt(LocalDateTime.now());

        // Update book availability
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        booksRepository.save(book);

        // Cancel any active reservations for this book by this user
        List<Reservation> activeReservations = reservationsRepository.findActiveReservationsByUserAndBook(userId, bookId);
        for (Reservation reservation : activeReservations) {
            reservation.setStatus(Reservation.ReservationStatus.FULFILLED);
            reservationsRepository.save(reservation);
        }

        Transactions savedTransaction = transactionsRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }
    @CacheEvict(value = {"allTransactions", "userAllTransactions"}, allEntries = true)
    public TransactionDTO returnBook(Integer transactionId) {
        Transactions transaction = transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));

        if (transaction.getStatus() != Transactions.TransactionStatus.ISSUED) {
            throw new RuntimeException("Book is not currently issued");
        }

        // Update transaction
        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus(Transactions.TransactionStatus.RETURNED);
        transaction.setUpdatedAt(LocalDateTime.now());

        // Update book availability
        Books book = transaction.getBook();
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        booksRepository.save(book);

        // Check for overdue and create fine if necessary
        if (LocalDate.now().isAfter(transaction.getDueDate())) {
            long overdueDays = LocalDate.now().toEpochDay() - transaction.getDueDate().toEpochDay();
            BigDecimal fineAmount = BigDecimal.valueOf(overdueDays * 5); // 5 per day fine

            Fine fine = new Fine();
            fine.setTransaction(transaction);
            fine.setFineAmount(fineAmount);
            fine.setFineDate(LocalDate.now());
            fine.setPaymentStatus(Fine.PaymentStatus.PENDING);
            fine.setReason("Overdue return - " + overdueDays + " days late");
            fineRepository.save(fine);
        }

        Transactions savedTransaction = transactionsRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }
    @CacheEvict(value = {"allTransactions", "userAllTransactions"}, allEntries = true)
    public String renewBook(Integer transactionId, Integer additionalDays) {
        Transactions transaction = transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));

        if (transaction.getStatus() != Transactions.TransactionStatus.ISSUED) {
            throw new RuntimeException("Book is not currently issued");
        }

        // Check if book has reservations
        List<Reservation> activeReservations = reservationsRepository.findActiveReservationsByBook(transaction.getBook().getBookId());
        if (!activeReservations.isEmpty()) {
            throw new RuntimeException("Cannot renew book as it has active reservations");
        }

        transaction.setDueDate(transaction.getDueDate().plusDays(additionalDays));
        transaction.setUpdatedAt(LocalDateTime.now());
        transactionsRepository.save(transaction);
        return "Successful renewBook";
    }

    private TransactionDTO convertToDTO(Transactions transaction) {
        // Get fine information
        List<Fine> fines = fineRepository.findByTransactionTransactionId(transaction.getTransactionId());
        BigDecimal totalFine = fines.stream()
                .map(Fine::getFineAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String fineStatus = fines.isEmpty() ? "NO_FINE" :
                fines.stream().allMatch(f -> f.getPaymentStatus() == Fine.PaymentStatus.PAID) ? "PAID" : "PENDING";

        return new TransactionDTO(
                transaction.getTransactionId(),
                transaction.getUser().getUserId(),
                transaction.getUser().getName(),
                transaction.getBook().getBookId(),
                transaction.getBook().getTitle(),
                transaction.getBook().getAuthor(),
                transaction.getIssueDate(),
                transaction.getDueDate(),
                transaction.getReturnDate(),
                transaction.getStatus().toString(),
                transaction.getNotes(),
                totalFine,
                fineStatus
        );
    }
}
