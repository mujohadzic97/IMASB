package com.amteam.requestmicroservice.controllers;

import com.amteam.requestmicroservice.entities.Client;
import com.amteam.requestmicroservice.services.ClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    private final ClientService clientService;
    @Value("${service.management.serviceId}")
    private String clientSearchServiceId;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public ResponseEntity<?> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getClientById(@PathVariable int clientId){
        return clientService.getClientById(clientId);
    }

    @PostMapping("/client")
    public ResponseEntity<?> addClient (@RequestBody Client client){
        return  clientService.addClient(client);
    }
}

