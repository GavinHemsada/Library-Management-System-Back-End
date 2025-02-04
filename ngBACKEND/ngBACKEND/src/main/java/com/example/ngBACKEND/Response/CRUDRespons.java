package com.example.ngBACKEND.Response;

public class CRUDRespons {
    private String message;
    private Boolean status;
    private String token;

    public CRUDRespons(String message, Boolean status, String token) {
        this.message = message;
        this.status = status;
        this.token = token;
    }
    public CRUDRespons(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
    public CRUDRespons(String message) {
        this.message = message;
    }

    public CRUDRespons(Boolean status) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegisterRespons{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", token='" + token + '\'' +
                '}';
    }
}