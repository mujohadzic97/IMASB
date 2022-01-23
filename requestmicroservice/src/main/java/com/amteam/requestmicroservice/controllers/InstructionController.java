package com.amteam.requestmicroservice.controllers;

import com.amteam.requestmicroservice.Models.InstructionRequest;
import com.amteam.requestmicroservice.entities.Instruction;
import com.amteam.requestmicroservice.services.InstructionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
public class InstructionController {

    private final InstructionService instructionService;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;

    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @GetMapping("instructions/{instructorId}")
    public ResponseEntity<?> getInstructionsByInstructorId(@PathVariable long instructorId, @RequestHeader("Authorization") String token){
        return instructionService.getInstructionsForInstructor(instructorId, token);
    }

    @GetMapping("instructions-client/{clientId}")
    public ResponseEntity<?> getInstructionsByClientId(@PathVariable long clientId, @RequestHeader("Authorization") String token){
        return instructionService.getInstructionsForClient(clientId, token);
    }

    @GetMapping("instructions/{instructorId}/{subjectId}")
    public ResponseEntity<?> getInstructionsByInstructorIdAndSubjectId(@PathVariable long instructorId, @PathVariable long subjectId, @RequestHeader("Authorization") String token){
        return instructionService.getInstructionsForInstructorAndSubject(instructorId, subjectId, token);
    }

    @GetMapping("instructions-subject/{subjectId}")
    public ResponseEntity<?> getInstructionsBySubjectId(@PathVariable long subjectId, @RequestHeader("Authorization") String token){
        return instructionService.getInstructionsForSubject(subjectId, token);
    }

    @GetMapping("stats-instructions")
    public ResponseEntity<?> getStatsInstructionsSubjectId( @RequestHeader("Authorization") String token){
        return instructionService.getStatsInstructionsSubject(token);
    }

    @PostMapping("/instruction/{instructorId}/{clientId}/{subjectId}")
    public ResponseEntity<?> addInstruction(@PathVariable long instructorId, @PathVariable long clientId, @PathVariable long subjectId, @RequestBody InstructionRequest instructionRequest, @RequestHeader("Authorization") String token){
        return instructionService.addInstruction(instructorId, clientId, subjectId, instructionRequest, token);
    }

}
