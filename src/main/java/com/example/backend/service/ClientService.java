package com.example.backend.service;

import com.example.backend.model.Client;
import com.example.backend.model.ClientDTO;
import com.example.backend.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final DTOConverter dtoConverter;
    private final String secretKey = "JHKLXABYZC!!!!";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    public ClientService(ClientRepository clientRepository, DTOConverter dtoConverter) {
        this.clientRepository = clientRepository;
        this.dtoConverter = dtoConverter;
    }

    public String createClient(ClientDTO clientDTO) {
        clientDTO.setPassword(PasswordManager.encrypt(clientDTO.getPassword(), secretKey));
        String validityOfClientData = AccountsValidator.isClientAccountValid(clientDTO);
        if (validityOfClientData.equals("valid")) {

            if (clientRepository.findByUsername(clientDTO.getUsername()).isPresent()) {
                LOGGER.info("The username: " + clientDTO.getUsername() + " is already taken.");
                return "Username already taken.";
            } else {
                if (clientRepository.findByEmail(clientDTO.getEmail()).isPresent()) {
                    LOGGER.info("The email address: " + clientDTO.getEmail() + " is already taken.");
                    return "This email address is already used.";
                }
            }
            clientRepository.save(dtoConverter.fromDTOtoClient(clientDTO));

            return "The account has been successfully created.";
        } else {
            return validityOfClientData;
        }
    }



    public ClientDTO getClientWithUserAndPass(String username, String password) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if (client.isPresent()) {

            if (Objects.equals(PasswordManager.decrypt(client.get().getPassword(), secretKey), password)) {
                return dtoConverter.fromClienttoDTO(client.get());
            }
        }
        return null;
    }

    public List<ClientDTO> getClients(){
        Iterable<Client> iterableClients = clientRepository.findAll();
        ArrayList<Client> clients= new ArrayList<>();
        iterableClients.forEach(clients::add);
        return clients.stream().map(dtoConverter::fromClienttoDTO).toList();
    }
    public String updateClientWithUsername(ClientDTO clientDTO) {
        Optional<Client> client = clientRepository.findByUsername(clientDTO.getUsername());
        if (client.isPresent()) {
            Client databaseClient=client.get();
            databaseClient.setFirstName(clientDTO.getFirstName());
            databaseClient.setLastName(clientDTO.getLastName());
            databaseClient.setEmail(clientDTO.getEmail());
            if(!Objects.equals(clientDTO.getPassword(), "")) {
                databaseClient.setPassword(PasswordManager.encrypt(clientDTO.getPassword(), secretKey));
            }
            clientRepository.save(databaseClient);
            return "The account has been successfully updated.";
        }
        return null;
    }

    public String deleteClientWithUsername(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if (client.isPresent()) {
            client.get().getDevices().forEach(el->el.setClient(null));
            clientRepository.delete(client.get());
            return "The account has been successfully deleted.";
        }
        return null;
    }
}
