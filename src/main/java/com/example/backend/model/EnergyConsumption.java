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
    private Timestamp timestamp;

    @Column(nullable = false)
    private int value;

    @ManyToOne
    private Device device;

    public EnergyConsumption() {

    }

    public EnergyConsumption(Timestamp timestamp, int value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
