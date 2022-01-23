package com.amteam.messagingmicroservice.services;


import com.amteam.messagingmicroservice.entities.Client;
import com.amteam.messagingmicroservice.entities.ErrorMessage;
import com.amteam.messagingmicroservice.repositories.ClientRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientService {
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;
    private final ClientRepository clientRepository;
    private final ErrorMessage errorMessage = new ErrorMessage("Something went wrong!");

    public ClientService(ClientRepository clientRepository, RestTemplate restTemplate, EurekaClient eurekaClient) {
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
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
            Application application = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/client/" + clientId;
            System.out.println("URL" + url);
            return restTemplate.getForEntity(url, Client.class);
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException)
                return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> insertNewClient(Client client) {
        try {
            if (client.isNotValid())
                return new ResponseEntity<>(new ErrorMessage("Password must contain a number!"), HttpStatus.BAD_REQUEST);
            Client existClient = clientRepository.findByUserName(client.getUserName());
            if (existClient != null)
                return new ResponseEntity<>(new ErrorMessage("User with this username already exists"), HttpStatus.BAD_REQUEST);
            Client clientDB = clientRepository.save(client);
            return new ResponseEntity<>(clientDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
