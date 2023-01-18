package com.example.backend.model;

import java.sql.Timestamp;

public class EnergyConsumptionDTO {
    private String timestamp;

    private int value;

    public EnergyConsumptionDTO(String timestamp, int value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
