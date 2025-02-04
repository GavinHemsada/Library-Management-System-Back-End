package com.example.ngBACKEND.DTO;

public class BooksDTO {
    private int ID;
    private String TITlE;
    private String AUTHOR;
    private String PRICE;
    private String CATEGORY;
    private String COPIES;

    public BooksDTO(int ID, String TITlE, String AUTHOR, String PRICE, String CATEGORY, String COPIES) {
        this.ID = ID;
        this.TITlE = TITlE;
        this.AUTHOR = AUTHOR;
        this.PRICE = PRICE;
        this.CATEGORY = CATEGORY;
        this.COPIES = COPIES;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTITlE() {
        return TITlE;
    }

    public void setTITlE(String TITlE) {
        this.TITlE = TITlE;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getCOPIES() {
        return COPIES;
    }

    public void setCOPIES(String COPIES) {
        this.COPIES = COPIES;
    }
}
