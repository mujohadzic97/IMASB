package com.amteam.ratingmicroservice.services;

import com.amteam.ratingmicroservice.entities.Client;
import com.amteam.ratingmicroservice.entities.ErrorMessage;
import com.amteam.ratingmicroservice.entities.Instructor;
import com.amteam.ratingmicroservice.repositories.ClientRepository;
import com.amteam.ratingmicroservice.repositories.InstructorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InstructorService {
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;
    private final InstructorRepository instructorRepository;
    private final ErrorMessage errorMessage =  new ErrorMessage("Something went wrong!");


    public InstructorService(InstructorRepository instructorRepository, RestTemplate restTemplate, @Qualifier("eurekaClient") EurekaClient eurekaClient) {
        this.instructorRepository = instructorRepository;
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
    }

    public ResponseEntity<?> getInstructorList() {
        try {

            List<Instructor> instructorList = instructorRepository.findAll();
            return new ResponseEntity<>(instructorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getInstructorById(int instructorId) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructor/" + instructorId;
        System.out.println("URL" + url);
        return restTemplate.getForEntity(url, Instructor.class);
    }

    public ResponseEntity<?> insertNewInstructor(Instructor instructor) {
        try {
            if(instructor.isNotValid())
                return new ResponseEntity<>( new ErrorMessage("Average grade can't be higher 5 or lower then 0!"), HttpStatus.BAD_REQUEST);
            if(instructorRepository.findByUserName(instructor.getUserName())!=null)
                return new ResponseEntity<>( new ErrorMessage("User with this username already exists"),HttpStatus.BAD_REQUEST);
            Instructor instructorDB = instructorRepository.save(instructor);
            return new ResponseEntity<>(instructorDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
