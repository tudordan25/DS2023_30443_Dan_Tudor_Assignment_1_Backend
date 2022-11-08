package com.example.backend.model;

public class AdminDTO {
    private String username;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AdminDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public AdminDTO() {}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
