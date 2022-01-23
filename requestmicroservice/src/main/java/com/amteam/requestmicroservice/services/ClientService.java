package com.amteam.requestmicroservice.services;

import com.amteam.requestmicroservice.entities.Client;
import com.amteam.requestmicroservice.entities.ErrorMessage;
import com.amteam.requestmicroservice.repositories.KlijentRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ClientService {

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;
    private final KlijentRepository clientRepository;

    public ClientService(KlijentRepository clientRepository, RestTemplate restTemplate,  @Qualifier("eurekaClient") EurekaClient eurekaClient) {
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
    }

    public ResponseEntity<?> getAllClients(){
        try {
            Application application = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/clients";
            System.out.println("URL" + url);
            return restTemplate.getForEntity(url, Client[].class);
        } catch (Exception ex){
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong while getting clients", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    public ResponseEntity<?> getClientById (int id){
        try {
            Application application = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/client/" + id;
            System.out.println("URL" + url);
            return restTemplate.getForEntity(url, Client.class);
        } catch (Exception ex){
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong while getting client", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }


    public ResponseEntity<?> addClient(Client client) {
        try {
            if(client.getNumberOfScheduledClasses()>0) {
                ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST,"Invalid client", Collections.emptyList());
                return new ResponseEntity<>(error, error.getStatus());
            }
            Client clientDB = clientRepository.save(client);
            return new ResponseEntity<>(clientDB, HttpStatus.OK);
        } catch (Exception e) {
            List<String> errors = new ArrayList<String>();
            errors.add(e.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong while adding client", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

}
