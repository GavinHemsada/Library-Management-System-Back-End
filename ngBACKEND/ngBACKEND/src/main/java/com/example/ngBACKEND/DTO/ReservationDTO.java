package com.example.ngBACKEND.DTO;

import com.example.ngBACKEND.Entity.Reservation;
import com.example.ngBACKEND.Entity.Reservation.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {

    private int id;
    private int bookId;
    private int userId;
    private LocalDateTime reservedAt;
    private Status status;

    // Constructor
    public ReservationDTO(int id, int bookId, int userId, LocalDateTime reservedAt, Status status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.reservedAt = reservedAt;
        this.status = status;
    }

    public ReservationDTO(int bookId, int userId, LocalDateTime reservedAt, Status status) {
        this.bookId = bookId;
        this.userId = userId;
        this.reservedAt = reservedAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(LocalDateTime reservedAt) {
        this.reservedAt = reservedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
