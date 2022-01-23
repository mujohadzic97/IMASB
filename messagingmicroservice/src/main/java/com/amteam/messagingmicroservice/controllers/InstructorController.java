package com.amteam.messagingmicroservice.controllers;

import com.amteam.messagingmicroservice.entities.Instructor;
import com.amteam.messagingmicroservice.services.InstructorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RefreshScope
@RestController
public class InstructorController {


    private final InstructorService instructorService;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;


    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }
    @GetMapping("/instructors")
    public ResponseEntity<?> getInstructors(){
        return instructorService.getInstructorList();
    }
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<?> getInstructorById(@PathVariable int instructorId){
        return instructorService.getInstructorById(instructorId);
    }
    @PostMapping("/instructor")
    public ResponseEntity<?> insertInstructor(@RequestBody Instructor instructor){
        return instructorService.insertNewInstructor(instructor);
    }
}
