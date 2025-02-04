package com.example.ngBACKEND.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fines")
@Data
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transactions transaction;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Fine(int id, User user, Transactions transaction, Double amount, Status status) {
        this.id = id;
        this.user = user;
        this.transaction = transaction;
        this.amount = amount;
        this.status = status;
    }
    public Fine(User user, Transactions transaction, Double amount, Status status) {
        this.user = user;
        this.transaction = transaction;
        this.amount = amount;
        this.status = status;
    }

    public Fine() {

    }

    public enum Status {
        UNPAID, PAID
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

