package com.example.backend.model;

public class TextMessageDTO {
    private String message;

    public TextMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
