package com.amteam.requestmicroservice.services;

import com.amteam.requestmicroservice.Models.InstructionRequest;
import com.amteam.requestmicroservice.Models.RequestServicePublisher;
import com.amteam.requestmicroservice.entities.*;
import com.amteam.requestmicroservice.repositories.*;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InstructionService {
    private final InstrukcijaReposiotry instructionRepository;
    private final CourseRepository courseRepository;
    private final InstruktorRepository instructorRepository;
    private final SubjectRepository subjectRepository;
    private final KlijentRepository clientRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    private final RequestServicePublisher publisher;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;





    public InstructionService(InstrukcijaReposiotry instructionRepository, CourseRepository courseRepository, InstruktorRepository instructorRepository, SubjectRepository subjectRepository, KlijentRepository clientRepository, RestTemplate restTemplate, @Qualifier("eurekaClient") EurekaClient eurekaClient, RequestServicePublisher publisher) {
        this.instructionRepository = instructionRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.subjectRepository = subjectRepository;
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
        this.publisher = publisher;
    }

    public ResponseEntity<?> getInstructionsForInstructor(Long instructorId, String token) {
        try {
            Instructor instructor = getInst(instructorId, token);
            if (instructor == null) {
                ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND, "Invalid instructor id", Collections.emptyList());
                return new ResponseEntity<>(error, error.getStatus());
            }
            System.out.println("Problem u bazi");
            List<Instruction> instructions = instructionRepository.findByInstructorId(instructorId);
            return new ResponseEntity<>(instructions, HttpStatus.OK);
        } catch (Exception ex) {
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while getting instructions for instructor", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }


    public ResponseEntity<?> getInstructionsForSubject(Long subjectId, String token){
        try{
            Subject subject = getSubject(subjectId, token);
            if(subjectId == null){
                ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND,"Invalid subject id", Collections.emptyList());
                return new ResponseEntity<>(error, error.getStatus());
            }
            List<Instruction> instructions = instructionRepository.findBySubjectId(subjectId);
            return new ResponseEntity<>(instructions, HttpStatus.OK);
        } catch (Exception ex) {
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong while getting instructions for instructor", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    public ResponseEntity<?> getStatsInstructionsSubject(String token){
        try{
            List<Subject> subjects = getSubjects(token);

            List<Stat> stats = new ArrayList<>();
            for(int i=0;i<subjects.size();i++){
                List<Instruction> instructions = instructionRepository.findBySubjectId(subjects.get(i).getId());
                List<Course> courses = courseRepository.findBySubjectId(subjects.get(i).getId());
                Stat stat = new Stat(subjects.get(i).getId(),subjects.get(i).getName(),subjects.get(i).getDescription(),instructions.size(), courses.size());
                stats.add(stat);
            }
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (Exception ex) {
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong while getting instructions for instructor", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    public ResponseEntity<?> getInstructionsForClient(Long clientId, String token){
        try{
            Client client = getClient(clientId, token);
            if (client == null) {
                ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND, "Invalid instructor id", Collections.emptyList());
                return new ResponseEntity<>(error, error.getStatus());
            }
            List<Instruction> instructions = instructionRepository.findByClientId(clientId);
            return new ResponseEntity<>(instructions, HttpStatus.OK);
        } catch (Exception ex) {
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while getting instructions for instructor", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    public ResponseEntity<?> getInstructionsForInstructorAndSubject(Long instructorId, Long subjectId, String token) {
        try {
            Instructor instructor = getInst(instructorId, token);
            Subject subject = getSubject(subjectId, token);
            if (instructor == null || subject == null) {
                ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND, "Invalid subject or incstructor id", Collections.emptyList());
                return new ResponseEntity<>(error, error.getStatus());
            }
            List<Instruction> instructions = instructionRepository.findByInstructorIdAndSubjectId(instructorId, subjectId);
            return new ResponseEntity<>(instructions, HttpStatus.OK);
        } catch (Exception ex) {
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while getting instructions for instructor and subject", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

    public Instructor getInst(Long id, String token) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructor/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service", "Request microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Instructor> instructorResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Instructor.class);
        return instructorResponseEntity.getBody();
    }

    public List<Subject> getSubjects(String token) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/subjects";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service","Rating microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Subject[]> subjectResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Subject[].class);

        Subject[] subjects = subjectResponseEntity.getBody();
        List<Subject> subjectList = new ArrayList<>(0);
        for(int i=0;i<subjects.length;i++){
            subjectList.add(subjects[i]);
        }
        return subjectList;
    }

    public List<Instructor> getInstructors(String token) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructors";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service","Rating microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Instructor[]> instructorResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Instructor[].class);

        Instructor[] instructors = instructorResponseEntity.getBody();
        List<Instructor> instructorList = new ArrayList<>(0);
        for(int i=0;i<instructors.length;i++){
            instructorList.add(instructors[i]);
        }
        return instructorList;
    }

    public Client getClient(Long id, String token) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/client/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service", "Request microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Client> clientResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Client.class);
        return clientResponseEntity.getBody();
    }

    public Subject getSubject(Long id, String token) {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/subject/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Subject> clientResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Subject.class);
        return clientResponseEntity.getBody();
    }

    public boolean updateInstructorsInstructions(long instructorId, int numberOfClasses, String token) {
        try {
            Application application = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/update-instructions/" + instructorId + "/" + numberOfClasses;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.set("Service", "Request microservice");
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            restTemplate.exchange(url, HttpMethod.GET, entity, Instructor.class);
            System.out.println("Get the right call");
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException)
                return false;
            System.out.println(e.getMessage());
            return true;
        }
        return true;
    }

    public ResponseEntity<?> addInstruction(Long instructorId, Long clientId, Long subjectId, InstructionRequest inst, String token) {
        try {

            Instruction instruction = new Instruction(subjectId, inst.getScheduledDate(), inst.getNumberOfClasses(), instructorId, clientId);
            //Instruction instruction = new Instruction(subject.getId(), inst.getScheduledDate(), inst.getNumberOfClasses(), instructor.getId(), client.getId());

            //getting number of instructions before adding new one

            if (!updateInstructorsInstructions(instructorId, inst.getNumberOfClasses(), token))
                return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST, "Bad request", "Instructor already has max instructions"), HttpStatus.BAD_REQUEST);

            int numberOfInstructionsForInstructor = instructionRepository.findByInstructorId(instructorId).size();
            Instruction savedInst = instructionRepository.save(instruction);

            publisher.sendInstructionAdded(numberOfInstructionsForInstructor, instructorId, savedInst.getId());

            //InstructorInstructions DTO = new InstructorInstructions(numberOfInstructionsForInstructor, instructorId, savedInst.getId());
            //rabbitTemplate.convertAndSend("nova-instrukcija","request.management", objectMapper.writeValueAsString(DTO));
            return new ResponseEntity<>(savedInst, HttpStatus.OK);
        } catch (Exception ex) {
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while adding instruction", errors);
            return new ResponseEntity<>(error, error.getStatus());
        }
    }

}
