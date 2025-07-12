package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.ReservationDTO;
import com.example.ngBACKEND.Entity.*;
import com.example.ngBACKEND.Repostry.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Cacheable(value = "allReservations")
    public List<ReservationDTO> getAllReservations() {
        return reservationsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReservationDTO> getReservationById(Integer id) {
        return reservationsRepository.findById(id).map(this::convertToDTO);
    }
    @Cacheable(value = "UserAllReservations" , key = "#userId")
    public List<ReservationDTO> getReservationsByUser(Integer userId) {
        return reservationsRepository.findByUserUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByBook(Integer bookId) {
        return reservationsRepository.findByBookBookId(bookId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByStatus(Reservation.ReservationStatus status) {
        return reservationsRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getActiveReservationsByUser(Integer userId) {
        return reservationsRepository.findActiveReservationsByUser(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getExpiredReservations() {
        return reservationsRepository.findExpiredReservations(LocalDate.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO createReservation(Integer userId, Integer bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        // Check if book is available
        if (book.getCopiesAvailable() > 0) {
            throw new RuntimeException("Book is available for immediate issue. No reservation needed.");
        }

        // Check if user already has active reservation for this book
        Long existingReservations = reservationsRepository.countActiveReservationsByUserAndBook(userId, bookId);
        if (existingReservations > 0) {
            throw new RuntimeException("User already has an active reservation for this book");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.now());
        reservation.setExpiryDate(LocalDate.now().plusDays(7)); // 7 days expiry
        reservation.setStatus(Reservation.ReservationStatus.ACTIVE);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(LocalDateTime.now());

        Reservation savedReservation = reservationsRepository.save(reservation);
        return convertToDTO(savedReservation);
    }
    @CacheEvict(value = {"allReservations", "UserAllReservations"}, allEntries = true)
    public String cancelReservation(Integer reservationId) {
        Reservation reservation = reservationsRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservationId));

        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        reservation.setUpdatedAt(LocalDateTime.now());
        reservationsRepository.save(reservation);
        return "Successful cancel Reservation";
    }
    @CacheEvict(value = {"allReservations", "UserAllReservations"}, allEntries = true)
    public String  expireReservation(Integer reservationId) {
        Reservation reservation = reservationsRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservationId));

        reservation.setStatus(Reservation.ReservationStatus.EXPIRED);
        reservation.setUpdatedAt(LocalDateTime.now());
        reservationsRepository.save(reservation);
        return "Successful expireReservation";
    }
    @CacheEvict(value = {"allReservations", "UserAllReservations"}, allEntries = true)
    public String  processExpiredReservations() {
        List<Reservation> expiredReservations = reservationsRepository.findExpiredReservations(LocalDate.now());
        for (Reservation reservation : expiredReservations) {
            reservation.setStatus(Reservation.ReservationStatus.EXPIRED);
            reservationsRepository.save(reservation);
        }
        return "processExpiredReservations";
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getReservationId(),
                reservation.getUser().getUserId(),
                reservation.getUser().getName(),
                reservation.getBook().getBookId(),
                reservation.getBook().getTitle(),
                reservation.getBook().getAuthor(),
                reservation.getReservationDate(),
                reservation.getExpiryDate(),
                reservation.getStatus().toString()
        );
    }
}