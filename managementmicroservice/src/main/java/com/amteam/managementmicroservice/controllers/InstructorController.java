package com.amteam.managementmicroservice.controllers;

import com.amteam.managementmicroservice.entities.ErrorMessage;
import com.amteam.managementmicroservice.entities.Instructor;
import com.amteam.managementmicroservice.repositories.InstructorRepository;
import com.amteam.managementmicroservice.services.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InstructorController {
    private final InstructorService instructorService;
    private final InstructorRepository instructorRepository;
    public InstructorController(InstructorService instructorService, InstructorRepository instructorRepository) {
        this.instructorService = instructorService;
        this.instructorRepository = instructorRepository;
    }
    @GetMapping("/instructors")
    public ResponseEntity<?> getInstructors(){
        return instructorService.getInstructorList();
    }
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<?> getInstructorById(@RequestHeader("Authorization") String token,@PathVariable int instructorId){
        return instructorService.getInstructorById(instructorId);
    }
    @PostMapping("/instructor")
    public ResponseEntity<?> insertInstructor(@RequestBody Instructor instructor){
        return instructorService.insertNewInstructor(instructor);
    }
    @PostMapping("/instructor-correctAvg")
    public ResponseEntity<?> correctInstructor(@RequestBody Instructor instructor){
        return instructorService.correctAvgGrade(instructor);
    }
    @GetMapping("/instructors-registered-last-month")
    public ResponseEntity<?> getRegisteredLastMonth(){
        return instructorService.getRegisteredLastMonth();
    }
    @GetMapping("/update-instructions/{instructorId}/{numberOfInst}")
    public ResponseEntity<?> updateNumberOfInstructions(@PathVariable long instructorId, @PathVariable int numberOfInst){
        try{
            Instructor instructor = instructorRepository.findById(instructorId);
            if(instructor.getMaxNumberOfInstructions() < instructor.getNumberOfScheduledInstructions()+numberOfInst)
                return new ResponseEntity<>("Reached max number of instructions", HttpStatus.BAD_REQUEST);
            instructor.setNumberOfScheduledInstructions(instructor.getNumberOfScheduledInstructions()+numberOfInst);
            Instructor instructorDB = instructorRepository.save(instructor);
            return new ResponseEntity<>(instructorDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage("Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}