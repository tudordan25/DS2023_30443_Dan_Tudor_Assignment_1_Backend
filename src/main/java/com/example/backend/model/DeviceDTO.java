package com.example.backend.model;

import java.util.Set;

public class DeviceDTO {
    private long id;
    private String description;
    private String address;
    private int consumption;

    private Set<EnergyConsumptionDTO> energyConsumption;

    private String clientUsername;

    public DeviceDTO(long id, String description, String address, int consumption, Set<EnergyConsumptionDTO> energyConsumption, String clientUsername) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.consumption = consumption;
        this.energyConsumption = energyConsumption;
        this.clientUsername = clientUsername;
    }

    public DeviceDTO() {}

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public int getConsumption() {
        return consumption;
    }

    public Set<EnergyConsumptionDTO> getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Set<EnergyConsumptionDTO> energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
