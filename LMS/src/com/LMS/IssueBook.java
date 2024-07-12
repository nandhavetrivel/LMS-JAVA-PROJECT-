package com.LMS;

import java.io.Serializable;

public class IssueBook implements Serializable {
    private int bookId;
    private int userId;

    // Constructor
    public IssueBook(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    // Getters and setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
