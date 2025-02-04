package com.example.ngBACKEND.Entity;

import jakarta.persistence.*;
@Entity
@Table(name = "books")
public class Books {
    @Id
    @Column(name="bookid", length = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="bookTitel", length = 100,nullable = false, unique = true)
    private String titel;

    @Column(name="bookAuther", length = 100 , nullable = false)
    private String auther;
    @Column(name="bookPrice", length = 100 , nullable = false)
    private String price;

    @Column(name="bookCategory", length = 100 , nullable = false)
    private String category;
    @Column(name = "bookCopies", length = 100, nullable = false)
    private String copies;

    public Books(){}
    public Books(int id, String titel, String auther, String price, String category, String copies) {
        this.id = id;
        this.titel = titel;
        this.auther = auther;
        this.price = price;
        this.category = category;
        this.copies = copies;
    }
    public Books(String titel, String auther, String price, String category, String copies) {
        this.titel = titel;
        this.auther = auther;
        this.price = price;
        this.category = category;
        this.copies = copies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }
}
