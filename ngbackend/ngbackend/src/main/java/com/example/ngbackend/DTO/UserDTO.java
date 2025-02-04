package com.example.ngbackend.DTO;

public class UserDTO {
    private int ID;
    private String EMAIL;
    private String PASSWORD;

    public UserDTO(int ID, String EMAIL, String PASSWORD) {
        this.ID = ID;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
