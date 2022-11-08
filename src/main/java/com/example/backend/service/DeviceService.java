package com.example.backend.service;

import com.example.backend.model.Client;
import com.example.backend.model.Device;
import com.example.backend.model.DeviceDTO;
import com.example.backend.repository.ClientRepository;
import com.example.backend.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final ClientRepository clientRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DTOConverter dtoConverter;

    public DeviceService(DeviceRepository deviceRepository,DTOConverter dtoConverter,ClientRepository clientRepository) {
        this.deviceRepository = deviceRepository;
        this.clientRepository=clientRepository;
        this.dtoConverter=dtoConverter;
    }

    public List<DeviceDTO> getDevices(){
        Iterable<Device> iterableMeteringDevices = deviceRepository.findAll();
        ArrayList<Device> meteringDevices= new ArrayList<>();
        iterableMeteringDevices.forEach(meteringDevices::add);
        return meteringDevices.stream().map(dtoConverter::fromDevicetoDTO).toList();
    }

    public String createDevice(DeviceDTO deviceDTO) {

        try {
            deviceRepository.save(dtoConverter.fromDTOtoDevice(deviceDTO));
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            String errorMessage="Could not create metering device: \n Description: "+deviceDTO.getDescription() + "\n Address: " + deviceDTO.getAddress();
            LOGGER.error(errorMessage);
            return null;
        }

        return "The metering device has been successfully created.";
    }
    public String updateDeviceById(DeviceDTO deviceDTO) {
        Optional<Device> meteringDevice = deviceRepository.findById(deviceDTO.getId());

        if (meteringDevice.isPresent()) {
            Device databaseMeteringDevice=meteringDevice.get();
            databaseMeteringDevice.setDescription(deviceDTO.getDescription());
            databaseMeteringDevice.setAddress(deviceDTO.getAddress());
            databaseMeteringDevice.setConsumption(deviceDTO.getConsumption());
            Optional<Client> client=clientRepository.findByUsername(deviceDTO.getClientUsername());
            if(client.isPresent()) {
                databaseMeteringDevice.setClient(client.get());
            }
            else{
                LOGGER.error("The username: "+deviceDTO.getClientUsername()+" intended to be tied with the metering device with id: "+ deviceDTO.getId()+" does not exist!");

                return null;
            }
            deviceRepository.save(databaseMeteringDevice);

            return "The metering device has been successfully updated.";
        }

        return null;
    }

    public String deleteDeviceWithId(String id) {
        long mdId;
        try{
            mdId=Long.parseLong(id);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
            return "Invalid metering device id: " + id;
        }

        Optional<Device> meteringDevice = deviceRepository.findById(mdId);
        if (meteringDevice.isPresent()) {
            deviceRepository.delete(meteringDevice.get());
            return "The metering device with id: " + id + " has been successfully deleted.";
        }
        return null;
    }

    public String linkDeviceToClient(String id,String username) {
        long mdId;
        try{
            mdId=Long.parseLong(id);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
            return "Invalid metering device id: " + id;
        }

        Optional<Device> meteringDevice = deviceRepository.findById(mdId);
        if (meteringDevice.isPresent()) {
            Optional<Client> client=clientRepository.findByUsername(username);
            if(client.isPresent()) {
                meteringDevice.get().setClient(client.get());
                deviceRepository.save(meteringDevice.get());
                return "The client with username: "+username+" has been successfully linked to metering device with id: " + id + ".";
            }
            else{
                return "The client with username: "+username+" was not found";
            }
        }
        return null;
    }
}
