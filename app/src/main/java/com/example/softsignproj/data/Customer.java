package com.example.softsignproj.data;

public class Customer {
    private String password;

    public Customer() {}

    public Customer(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
