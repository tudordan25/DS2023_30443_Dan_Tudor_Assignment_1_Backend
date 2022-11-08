package com.example.backend.controller;

import com.example.backend.model.ClientDTO;
import com.example.backend.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private final ClientService clientService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/loginClient/{username}/{password}")
    public ResponseEntity<ClientDTO> loginClient(@PathVariable String username, @PathVariable String password) {
        ClientDTO clientDTO = clientService.getClientWithUserAndPass(username, password);
        HttpStatus httpStatus = HttpStatus.OK;
        if (clientDTO == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username password combination of supposed user: " + username + " is invalid.");
            return ResponseEntity.status(httpStatus).body(new ClientDTO("", "", "", "", ""));
        } else {
            LOGGER.info("The user with the username: " + username + " has successfully logged in.");
        }
        return ResponseEntity.status(httpStatus).body(clientDTO);
    }
}
