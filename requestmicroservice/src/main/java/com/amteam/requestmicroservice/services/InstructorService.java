package com.amteam.requestmicroservice.services;

import com.amteam.requestmicroservice.Models.Comment;
import com.amteam.requestmicroservice.Models.InstructorExtended;
import com.amteam.requestmicroservice.entities.ErrorMessage;
import com.amteam.requestmicroservice.entities.Grade;
import com.amteam.requestmicroservice.entities.Instructor;
import com.amteam.requestmicroservice.repositories.InstrukcijaReposiotry;
import com.amteam.requestmicroservice.repositories.InstruktorRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class InstructorService {
    private final InstruktorRepository instructorRepository;
    private final InstrukcijaReposiotry instructionRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;
    @Value("${serice.rating.serviceId}")
    private String ratingServiceId;

    public InstructorService(InstruktorRepository instructorRepository, InstrukcijaReposiotry instructionRepository, RestTemplate restTemplate, @Qualifier("eurekaClient") EurekaClient eurekaClient) {
        this.instructorRepository = instructorRepository;
        this.instructionRepository = instructionRepository;
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
    }

    public ResponseEntity<?> getAllInstructors(String token){
        try {
            Application application = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructors";
            System.out.println("URL" + url);
            //return restTemplate.getForEntity(url, Instructor[].class);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            ResponseEntity<Instructor[]> instructorResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Instructor[].class);
            return instructorResponseEntity;
        } catch (Exception ex) {
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            if(ex instanceof HttpClientErrorException){
                return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Something went wrong while getting instructors", errors), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Something went wrong while getting instructors", errors), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getInstructorById(int id, String token){
       try {
            Application applicationManagement = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfoManagement = applicationManagement.getInstances().get(0);
            String urlManagaement = "http://" + instanceInfoManagement.getIPAddr() + ":" + instanceInfoManagement.getPort() + "/instructor/" + id;
            System.out.println("URL" + urlManagaement);
           HttpHeaders headers = new HttpHeaders();
           headers.setBearerAuth(token);
           HttpEntity<String> entity = new HttpEntity<>("body", headers);
            ResponseEntity<Instructor> responseInstructor = restTemplate.exchange(urlManagaement, HttpMethod.GET, entity, Instructor.class);
            Instructor instructorEF =  responseInstructor.getBody();

            int numberOfInstructions = instructionRepository.findByInstructorId(id).size();

           InstructorExtended instructorExtended = new InstructorExtended(instructorEF.getFirstName(),
                   instructorEF.getLastName(), instructorEF.getAvgGrade(), instructorEF.getDescription(), new ArrayList<>(instructorEF.getSubjects()), Collections.emptyList(), numberOfInstructions);

           return new ResponseEntity<>(instructorExtended, HttpStatus.OK);
       } catch (Exception ex){
           List<String> errors = new ArrayList<String>();
           errors.add(ex.getMessage());
            if(ex instanceof HttpClientErrorException){
                return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Something went wrong while getting instructor", errors), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while getting instructor", errors), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getCommentsForInstructor(int id){
        try{
            Application applicationRating = eurekaClient.getApplication(ratingServiceId);
            InstanceInfo instanceInfoRating = applicationRating.getInstances().get(0);
            String urlRating = "http://" + instanceInfoRating.getIPAddr() + ":" + instanceInfoRating.getPort() + "/grades-instructor-all/" + id;
            System.out.println("URL" + urlRating);
            ResponseEntity<Grade[]> responseGrades= restTemplate.getForEntity(urlRating, Grade[].class);
            Grade[] gradesEF = responseGrades.getBody();
            List<Comment> comments = new ArrayList<>(0);
            for (int i=0; i<gradesEF.length; i++){
                comments.add(new Comment(gradesEF[i].getComment(), gradesEF[i].getGrade()));
            }
            return new ResponseEntity<> (comments, HttpStatus.OK);
        } catch (Exception ex){
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            if(ex instanceof HttpClientErrorException){
                return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Something went wrong while getting grades for instructor", errors), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while getting grades for instructor", errors), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public  ResponseEntity<?> getTop5Instructors(String token){
        try {
            Application application = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructors";
            System.out.println("URL" + url);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            ResponseEntity<Instructor[]> responseInstructors= restTemplate.exchange(url, HttpMethod.GET, entity, Instructor[].class);
            Instructor[] instructorsEF =  responseInstructors.getBody();
            List<InstructorExtended> instructorsExtended = new ArrayList<>(0);
            int numberOfInstructions = 0;
            for (int i=0; i<instructorsEF.length; i++) {
                Instructor ins = instructorsEF[i];
                numberOfInstructions = instructionRepository.findByInstructorId(ins.getId()).size();
                instructorsExtended.add(new InstructorExtended(ins.getFirstName(),
                        ins.getLastName(), ins.getAvgGrade(), ins.getDescription(),
                        new ArrayList<>(ins.getSubjects()), Collections.emptyList(),
                        numberOfInstructions));
            }
            instructorsExtended.sort(Comparator.comparing(InstructorExtended::getNumberOfScheduledClasses).reversed());
            List<InstructorExtended> instructorsTop5 = new ArrayList<>(5);
            for(int i=0; i<instructorsExtended.size(); i++){
                instructorsTop5.add(instructorsExtended.get(i));
                if(i==4) break;
            }
            return new ResponseEntity<>(instructorsTop5, HttpStatus.OK);
        } catch (Exception ex) {
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            if(ex instanceof HttpClientErrorException){
                return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Something went wrong while getting instructors", errors), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND, "Something went wrong while getting instructors", errors), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> addInstructor(Instructor instructor) {
        try {
            List<String> errors = ValidateInstructor(instructor);
            if(!errors.isEmpty()){
                ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST, "Invalid instructor", errors);
                return new ResponseEntity<>(error, error.getStatus());
            }
            Instructor instructorDB = instructorRepository.save(instructor);
            return new ResponseEntity<>(instructorDB, HttpStatus.OK);
        } catch (Exception e) {
            List<String> errors = new ArrayList<String>();
            errors.add(e.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST, "Something went wrong while adding instructor", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    private List<String> ValidateInstructor (Instructor instructor){
        List<String> errors = Collections.emptyList();
        if (instructor.getDescription().length() < 10) {
            errors.add("Invalid description");
        }
        if (instructor.getAvgGrade() > 5 || instructor.getAvgGrade() < 1) {
            errors.add("Invalid grade");
        }
        return  errors;
    }
}



