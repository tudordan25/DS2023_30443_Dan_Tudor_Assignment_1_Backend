package com.example.backend.service;

import com.example.backend.model.Admin;
import com.example.backend.model.AdminDTO;
import com.example.backend.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final DTOConverter dtoConverter;
    private final String secretKey = "JHKLXABYZC!!!!";

    public AdminService(AdminRepository adminRepository, DTOConverter dtoConverter) {
        this.adminRepository = adminRepository;
        this.dtoConverter = dtoConverter;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    public String createAdmin(AdminDTO adminDTO){

        adminDTO.setPassword(PasswordManager.encrypt(adminDTO.getPassword(), secretKey));
        String validityOfAdminData=AccountsValidator.isAdminAccountValid(adminDTO);
        if(validityOfAdminData.equals("valid")) {
            if(adminRepository.findByUsername(adminDTO.getUsername()).isPresent()){
                LOGGER.info("The username:" + adminDTO.getUsername() + " is already used.");
                return "Username already taken.";

            }
            else{
                if(adminRepository.findByEmail(adminDTO.getEmail()).isPresent()){
                    LOGGER.info("The email:" +adminDTO.getEmail()+" is already used.");
                    return "This email address is already used.";
                }
            }
            adminRepository.save(dtoConverter.fromDTOtoAdmin(adminDTO));
            LOGGER.info("The account with the username:" +adminDTO.getUsername() +" and email: "+adminDTO.getEmail()+" has been successfully created.");
            return "The account has been successfully created.";
        }
        else{
            return validityOfAdminData;
        }
    }
    public AdminDTO getAdminWithUserAndPass(String username,String password){
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if(admin.isPresent()){
            if(Objects.equals(PasswordManager.decrypt(admin.get().getPassword(), secretKey), password)){
                return dtoConverter.fromAdmintoDTO(admin.get());
            }
            LOGGER.info("Wrong password for: "+ username);
        }
        LOGGER.info("Wrong username: "+ username);
        return null;
    }
}
