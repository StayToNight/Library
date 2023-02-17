package com.example.library.model;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private int year;
    private int quantity;

    public Book(String title, String author, String isbn, int year, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
