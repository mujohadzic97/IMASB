package com.amteam.managementmicroservice.controllers;

import com.amteam.managementmicroservice.repositories.AdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @DeleteMapping("/client/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long clientId){
        //TODO implement delete client
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/instructor/{instructorId}")
    public ResponseEntity<?> deleteInstructor(@PathVariable Long instructorId){
        //TODO implement delete client
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
