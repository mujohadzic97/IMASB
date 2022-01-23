package com.amteam.requestmicroservice.controllers;

import com.amteam.requestmicroservice.entities.Instructor;
import com.amteam.requestmicroservice.services.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/instructors")
    public ResponseEntity<?> getAllInstructors(@RequestHeader("Authorization") String token){
        return instructorService.getAllInstructors(token);
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<?> getInstructorById(@PathVariable int instructorId, @RequestHeader("Authorization") String token){
        ResponseEntity<?> responseInstructor = instructorService.getInstructorById(instructorId, token);
        return responseInstructor;
    }

    @GetMapping("/instructorsTop5")
    public ResponseEntity<?> getTop5Instructors(@RequestHeader("Authorization") String token){
        ResponseEntity<?> responseInstructor = instructorService.getTop5Instructors(token);
        return responseInstructor;
    }

    @GetMapping("/instructor-comments/{instructorId}")
    public ResponseEntity<?> getCommentsForInstructor(@PathVariable int instructorId){
        ResponseEntity<?> responseInstructor = instructorService.getCommentsForInstructor(instructorId);
        return responseInstructor;
    }

    @PostMapping("/instructor")
    public ResponseEntity<?> addInstructor(@RequestBody Instructor instructor){
        return instructorService.addInstructor(instructor);
    }

}
