package com.amteam.managementmicroservice.services;

import com.amteam.managementmicroservice.entities.Client;
import com.amteam.managementmicroservice.entities.ErrorMessage;
import com.amteam.managementmicroservice.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ErrorMessage errorMessage = new ErrorMessage("Something went wrong!");

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<?> getClientsList() {
        try {

            List<Client> clientList = clientRepository.findAll();
            return new ResponseEntity<>(clientList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getClientById(int clientId) {
        try {
            Client client = clientRepository.findById(clientId);
            if(client == null)
                return new ResponseEntity<>(new ErrorMessage("No user found!"),HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> insertNewClient(Client client) {
        try {
            if(client.isNotValid())
                return new ResponseEntity<>( new ErrorMessage("Password must contain a number!"), HttpStatus.BAD_REQUEST);
            Client existClient = clientRepository.findByUserName(client.getUserName());
            if(existClient!=null)
                return new ResponseEntity<>( new ErrorMessage("User with this username already exists"),HttpStatus.BAD_REQUEST);
            client.setRegistrationDate(OffsetDateTime.now().toString());
            client.setRole(3);
            Client clientDB = clientRepository.save(client);
            return new ResponseEntity<>(clientDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}