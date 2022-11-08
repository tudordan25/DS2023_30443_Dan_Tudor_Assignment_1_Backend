package com.example.backend.service;

import com.example.backend.model.ClientDTO;
import com.example.backend.model.AdminDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountsValidator {
    private static Logger LOGGER = LoggerFactory.getLogger(AccountsValidator.class);

    private static String areCredentialsValid(String username, String email, String password) {

        LOGGER.info("Account with username: " + username + " and email address: " + email + " is verified...");
        if (username == null || username.equals("")) {
            LOGGER.info("Username cannot be empty");
            return "Username cannot be empty";
        }


        if (email == null || email.equals("")) {
            LOGGER.info("Email address cannot be empty");
            return "Email cannot be empty";
        }
        if (!email.contains("@") || !email.contains(".")) {
            LOGGER.info("Email address does not contain @ or .");
            return "Invalid email address.";
        }
        if (password == null || password.equals("")) {
            LOGGER.info("Password is empty.");
            return "Password cannot be empty";
        }
        LOGGER.info("Account with username: " + username + " and email address: " + email + " is valid.");
        return "valid";
    }

    public static String isClientAccountValid(ClientDTO Client) {

        String credentialsValidity = areCredentialsValid(Client.getUsername(), Client.getEmail(), Client.getPassword());
        if (!credentialsValidity.equals("valid")) {
            return credentialsValidity;
        } else {
            if (Client.getFirstName() == null || Client.getFirstName().equals("")) {
                LOGGER.info("First name is empty");
                return "First name field cannot be empty";
            }
            if (Client.getLastName() == null || Client.getLastName().equals("")) {
                LOGGER.info("Last name is empty");
                return "Last name field cannot be empty";
            }
        }

        return "valid";
    }

    public static String isAdminAccountValid(AdminDTO adminDTO) {
        return areCredentialsValid(adminDTO.getUsername(), adminDTO.getEmail(), adminDTO.getPassword());
    }

}
