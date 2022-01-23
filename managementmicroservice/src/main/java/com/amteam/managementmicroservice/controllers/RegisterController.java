package com.amteam.managementmicroservice.controllers;

import com.amteam.managementmicroservice.entities.Client;
import com.amteam.managementmicroservice.entities.Instructor;
import com.amteam.managementmicroservice.services.ClientService;
import com.amteam.managementmicroservice.services.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final InstructorService instructorService;
    private final ClientService clientService;
    public RegisterController(InstructorService instructorService, ClientService clientService) {
        this.instructorService = instructorService;
        this.clientService = clientService;
    }
    @PostMapping("/register-client")
    public ResponseEntity<?> registerClient(@RequestBody Client client){
        return clientService.insertNewClient(client);
    }
    @PostMapping("/register-instructor")
    public ResponseEntity<?> insertInstructor(@RequestBody Instructor instructor){
        return instructorService.insertNewInstructor(instructor);
    }
}
