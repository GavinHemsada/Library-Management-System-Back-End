package com.example.ngbackend.Respons;

public class LoginRespons {
    private String message;
    private Boolean status;

    public LoginRespons(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginRespons{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
