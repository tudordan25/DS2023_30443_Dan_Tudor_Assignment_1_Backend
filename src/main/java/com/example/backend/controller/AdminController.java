package com.example.backend.controller;

import com.example.backend.model.Admin;
import com.example.backend.model.AdminDTO;
import com.example.backend.model.ClientDTO;
import com.example.backend.model.DeviceDTO;
import com.example.backend.service.AdminService;
import com.example.backend.service.ClientService;
import com.example.backend.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    private final AdminService adminService;
    private final ClientService clientService;
    private final DeviceService deviceService;


    public AdminController(AdminService adminService, ClientService clientService, DeviceService deviceService) {
        this.adminService = adminService;
        this.clientService = clientService;
        this.deviceService = deviceService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @PostMapping("/addAdmin")
    public ResponseEntity<String> insertAdmin(@RequestBody AdminDTO adminDTO) {
        String status = adminService.createAdmin(adminDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username:" + adminDTO.getUsername() + " or the email:" + adminDTO.getEmail() + " is already taken.");
        } else {
            LOGGER.info("The admin account with the username: " + adminDTO.getUsername() + " and email: " + adminDTO.getEmail() + " was successfully created.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/loginAdmin/{username}/{password}")
    public ResponseEntity<AdminDTO> loginAdmin(@PathVariable String username, @PathVariable String password) {
        AdminDTO adminDTO = adminService.getAdminWithUserAndPass(username, password);
        HttpStatus httpStatus = HttpStatus.OK;
        if (adminDTO == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username password combination of supposed admin: " + username + " is invalid.");
            return ResponseEntity.status(httpStatus).body(new AdminDTO("", "", ""));
        } else {
            LOGGER.info("The admin with the username: " + username + " has successfully logged in.");
        }
        return ResponseEntity.status(httpStatus).body(adminDTO);
    }

    @PostMapping("/addClient")
    public ResponseEntity<String> insertClient(@RequestBody ClientDTO clientDTO) {
        String status = clientService.createClient(clientDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username:" + clientDTO.getUsername() + " or the email:" + clientDTO.getEmail() + " is already taken.");
        } else {
            LOGGER.info("The account with the username: " + clientDTO.getUsername() + " and email: " + clientDTO.getEmail() + " was successfully created.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/getClients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clients=clientService.getClients();
        HttpStatus httpStatus = HttpStatus.OK;
        if (clients.isEmpty()) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("There are no clients available at the moment.");
        }
        return ResponseEntity.status(httpStatus).body(clients);
    }

    @PutMapping("/updateClient")
    public ResponseEntity<String> updateClient(@RequestBody ClientDTO clientDTO) {
        String status = clientService.updateClientWithUsername(clientDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully updated.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The client with the username: "+clientDTO.getUsername() +" was not found.");
        } else {
            LOGGER.info("The account with the username: " + clientDTO.getUsername() + " was successfully updated.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @DeleteMapping("/deleteClient/{username}")
    public ResponseEntity<String> deleteClient(@PathVariable String username) {
        String status= clientService.deleteClientWithUsername(username);
        HttpStatus httpStatus = HttpStatus.OK;
        if (status == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username password combination of supposed admin: " + username + " is invalid.");

        } else {
            LOGGER.info("The client with the username: " + username + " has successfully been deleted.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/getDevices")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> devices= deviceService.getDevices();
        HttpStatus httpStatus = HttpStatus.OK;
        if (devices.isEmpty()) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("There are no metering devices available at the moment.");
        }
        return ResponseEntity.status(httpStatus).body(devices);
    }

    @PostMapping("/addDevice")
    public ResponseEntity<String> insertDevice(@RequestBody DeviceDTO deviceDTO) {
        String status = deviceService.createDevice(deviceDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The metering device has been successfully created.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        } else {
            LOGGER.info("Metering device: \n Description: "+deviceDTO.getDescription() + "\n Address: " + deviceDTO.getAddress() + "\n successfully created.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }
    @PutMapping("/updateDevice")
    public ResponseEntity<String> updateDevice(@RequestBody DeviceDTO deviceDTO) {
        LOGGER.info("############################################" + deviceDTO.getClientUsername());
        String status = deviceService.updateDeviceById(deviceDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The metering device has been successfully updated.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The metering device with the id: "+deviceDTO.getId() +" could not be updated.");
        } else {
            LOGGER.info("The metering device with the Id: " + deviceDTO.getId() + " was successfully updated.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @DeleteMapping("/deleteDevice/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable String id) {
        String status = deviceService.deleteDeviceWithId(id);
        HttpStatus httpStatus = HttpStatus.OK;

        if (status == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The id of supposed metering device: " + id  + " is invalid.");
        } else {
            LOGGER.info(status);
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/linkDeviceToClient/{id}/{username}")
    public ResponseEntity<String> linkDeviceToClient(@PathVariable String id,@PathVariable String username){
        String status = deviceService.linkDeviceToClient(id,username);
        HttpStatus httpStatus = HttpStatus.OK;
        if (status == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The id of supposed metering device: " + id  + " is invalid.");
        } else {
            LOGGER.info(status);
        }
        return ResponseEntity.status(httpStatus).body(status);
    }
}
