package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Books;
import com.example.ngBACKEND.Entity.Reservation;
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
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);

    List<Reservation> findByBook(Books book);

    List<Reservation> findByStatus(Reservation.ReservationStatus status);

    List<Reservation> findByUserUserId(Integer userId);

    List<Reservation> findByBookBookId(Integer bookId);

    @Query("SELECT r FROM Reservation r WHERE r.expiryDate < :currentDate AND r.status = 'ACTIVE'")
    List<Reservation> findExpiredReservations(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT r FROM Reservation r WHERE r.user.userId = :userId AND r.status = 'ACTIVE'")
    List<Reservation> findActiveReservationsByUser(@Param("userId") Integer userId);

    @Query("SELECT r FROM Reservation r WHERE r.book.bookId = :bookId AND r.status = 'ACTIVE'")
    List<Reservation> findActiveReservationsByBook(@Param("bookId") Integer bookId);

    @Query("SELECT r FROM Reservation r WHERE r.reservationDate BETWEEN :startDate AND :endDate")
    List<Reservation> findReservationsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Check if user has active reservation for a book
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.user.userId = :userId AND r.book.bookId = :bookId AND r.status = 'ACTIVE'")
    Long countActiveReservationsByUserAndBook(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
    @Query("SELECT r FROM Reservation r WHERE r.book.bookId = :bookId AND r.user.userId = :userId AND r.status = 'ACTIVE'")
    List<Reservation> findActiveReservationsByUserAndBook(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
