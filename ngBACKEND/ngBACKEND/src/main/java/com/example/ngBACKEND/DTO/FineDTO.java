package com.example.ngBACKEND.DTO;

import com.example.ngBACKEND.Entity.Fine;
import com.example.ngBACKEND.Entity.Fine.Status;
import lombok.Data;

@Data
public class FineDTO {

    private int id;
    private int userId;
    private int transactionId;
    private Double amount;
    private Status status;

    public FineDTO(int id, int userId, int transactionId, Double amount, Status status) {
        this.id = id;
        this.userId = userId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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
