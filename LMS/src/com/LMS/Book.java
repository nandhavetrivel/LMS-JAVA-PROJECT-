package com.LMS;

import java.io.Serializable;

public class Book implements Serializable {
    private static int count = 1;
    private int bookId;
    private String bookName;
    private String writerName;
    private double price;
    private int quantity;

    // Constructor
    public Book(String bookName, String writerName, double price, int quantity) {
        this.bookId = count++;
        this.bookName = bookName;
        this.writerName = writerName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters
    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Book.count = count;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookID: " + bookId + ", BookName: " + bookName + ", Writer: " + writerName + ", Price: " + price + ", Quantity: " + quantity;
    }
}