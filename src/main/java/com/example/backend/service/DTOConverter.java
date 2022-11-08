package com.example.backend.service;

import com.example.backend.model.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DTOConverter {
    public Client fromDTOtoClient(ClientDTO clientDTO){
        return new Client(clientDTO.getUsername(), clientDTO.getEmail(), clientDTO.getPassword(), clientDTO.getFirstName(), clientDTO.getLastName());
    }

    public Admin fromDTOtoAdmin(AdminDTO adminDTO)
    {
        return new Admin(adminDTO.getUsername(),adminDTO.getEmail(),adminDTO.getPassword());
    }

    public ClientDTO fromClienttoDTO(Client client){
        return new ClientDTO(client.getUsername(), client.getEmail() , client.getPassword(), client.getFirstName(), client.getLastName());
    }

    public AdminDTO fromAdmintoDTO(Admin admin){
        return new AdminDTO(admin.getUsername(), admin.getEmail(), admin.getPassword());
    }

    public EnergyConsumptionDTO fromEnergyConsumptiontoDTO(EnergyConsumption energyConsumption){
        return new EnergyConsumptionDTO(energyConsumption.getTimestamp(), energyConsumption.getValue());
    }

    public DeviceDTO fromDevicetoDTO(Device device){
        Client client = device.getClient();
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getConsumption(), device.getEnergyConsumptionSet().stream().map(this::fromEnergyConsumptiontoDTO).collect(Collectors.toSet()),client == null ? "": client.getUsername());
    }

    public Device fromDTOtoDevice(DeviceDTO deviceDTO){
        return new Device(deviceDTO.getDescription(), deviceDTO.getAddress(), deviceDTO.getConsumption());
    }
}
