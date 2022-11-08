package com.example.backend.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private int consumption;

    @OneToMany(mappedBy = "device")
    private Set<EnergyConsumption> energyConsumptionSet;

    @ManyToOne
    private Client client;


    public Device(String description, String address, int consumption) {
        this.description = description;
        this.address = address;
        this.consumption = consumption;
    }

    public Device() {}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public Set<EnergyConsumption> getEnergyConsumptionSet() {
        return energyConsumptionSet;
    }

    public void setEnergyConsumptionSet(Set<EnergyConsumption> energyConsumptionSet) {
        this.energyConsumptionSet = energyConsumptionSet;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
