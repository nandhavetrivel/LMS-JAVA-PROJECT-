package com.LMS;

import java.io.Serializable;

public class User implements Serializable {
    private static int count = 1;
    private int userId;
    private String password;
    private String role;

    // Constructor
    public User(String password, String role) {
        this.userId = count++;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        User.count = count;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserID: " + userId + ", Role: " + role;
    }
}

