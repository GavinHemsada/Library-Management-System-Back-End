package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Books;
import com.example.ngBACKEND.Entity.Transactions;
import com.example.ngBACKEND.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

    List<Transactions> findByUser(User user);

    List<Transactions> findByBook(Books book);

    List<Transactions> findByStatus(Transactions.TransactionStatus status);

    List<Transactions> findByUserUserId(Integer userId);

    List<Transactions> findByBookBookId(Integer bookId);

    @Query("SELECT t FROM Transactions t WHERE t.dueDate < :currentDate AND t.status = 'ISSUED'")
    List<Transactions> findOverdueTransactions(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT t FROM Transactions t WHERE t.user.userId = :userId AND t.status = 'ISSUED'")
    List<Transactions> findActiveTransactionsByUser(@Param("userId") Integer userId);

    @Query("SELECT t FROM Transactions t WHERE t.book.bookId = :bookId AND t.status = 'ISSUED'")
    List<Transactions> findActiveTransactionsByBook(@Param("bookId") Integer bookId);

    @Query("SELECT t FROM Transactions t WHERE t.issueDate BETWEEN :startDate AND :endDate")
    List<Transactions> findTransactionsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Check if user has any unreturned books
    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.user.userId = :userId AND t.status = 'ISSUED'")
    Long countUnreturnedBooksByUser(@Param("userId") Integer userId);

    // Check if book is currently issued
    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.book.bookId = :bookId AND t.status = 'ISSUED'")
    Long countActiveTransactionsByBook(@Param("bookId") Integer bookId);
}

