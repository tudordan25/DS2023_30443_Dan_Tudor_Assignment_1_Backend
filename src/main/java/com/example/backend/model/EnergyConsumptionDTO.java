package com.example.backend.model;

import java.sql.Timestamp;

public class EnergyConsumptionDTO {
    private Timestamp timestamp;

    private int value;

    public EnergyConsumptionDTO(Timestamp timestamp, int value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
