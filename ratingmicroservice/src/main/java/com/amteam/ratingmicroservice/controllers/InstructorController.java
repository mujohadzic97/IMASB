package com.amteam.ratingmicroservice.controllers;

import com.amteam.ratingmicroservice.entities.Instructor;
import com.amteam.ratingmicroservice.services.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class InstructorController {

    @Value("${service.management.serviceId}")
    private String clientSearchServiceId;

    private final InstructorService instructorService;
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }
    @GetMapping("/instructors")
    public ResponseEntity<?> getClients(){
        return instructorService.getInstructorList();
    }
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<?> getClientById(@PathVariable int instructorId){
        return instructorService.getInstructorById(instructorId);
    }
    @PostMapping("/instructor")
    public ResponseEntity<?> insertClient(@RequestBody Instructor instructor){
        return instructorService.insertNewInstructor(instructor);
    }
}
