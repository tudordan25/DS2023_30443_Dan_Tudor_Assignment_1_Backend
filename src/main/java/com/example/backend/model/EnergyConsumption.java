package com.example.backend.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "energy_consumption")
public class EnergyConsumption {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String timestamp;

    @Column(nullable = false)
    private int value;

    @ManyToOne
    private Device device;

    public EnergyConsumption() {

    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public EnergyConsumption(String timestamp, int value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
