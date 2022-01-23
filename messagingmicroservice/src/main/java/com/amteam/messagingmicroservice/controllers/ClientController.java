package com.amteam.messagingmicroservice.controllers;

import com.amteam.messagingmicroservice.entities.Client;
import com.amteam.messagingmicroservice.services.ClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {


    private final ClientService clientService;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;


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
