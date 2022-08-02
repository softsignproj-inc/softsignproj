package com.example.softsignproj.data;

public class Administrator {
    private String password;

    public Administrator() {}

    public Administrator(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
