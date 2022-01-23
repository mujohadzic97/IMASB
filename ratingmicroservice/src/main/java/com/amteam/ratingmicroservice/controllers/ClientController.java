package com.amteam.ratingmicroservice.controllers;

import com.amteam.ratingmicroservice.entities.Client;
import com.amteam.ratingmicroservice.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class ClientController {
    private final ClientService clientService;

    @Value("${service.management.serviceId}")
    private String clientSearchServiceId;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping("/clients")
    public ResponseEntity<?> getClients(){
        return clientService.getClientsList();
    }
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getClientById(@PathVariable int clientId){
        return clientService.getClientById(clientId);
    }
    @PostMapping("/client")
    public ResponseEntity<?> insertClient(@RequestBody Client client){
        return clientService.insertNewClient(client);
    }

}
