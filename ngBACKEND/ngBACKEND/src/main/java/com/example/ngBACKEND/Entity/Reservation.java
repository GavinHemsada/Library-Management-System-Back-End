package com.example.ngBACKEND.Entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Books book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime reservedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, COMPLETED, CANCELED
    }

    public Reservation(int id, Books book, User user, LocalDateTime reservedAt, Status status) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.reservedAt = reservedAt;
        this.status = status;
    }
    public Reservation(Books book, User user, LocalDateTime reservedAt, Status status) {
        this.book = book;
        this.user = user;
        this.reservedAt = reservedAt;
        this.status = status;
    }
    public Reservation(LocalDateTime reservedAt, Status status) {
        this.reservedAt = reservedAt;
        this.status = status;
    }
    public Reservation(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
