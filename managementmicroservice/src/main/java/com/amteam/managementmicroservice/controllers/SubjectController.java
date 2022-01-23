package com.amteam.managementmicroservice.controllers;

import com.amteam.managementmicroservice.entities.Subject;
import com.amteam.managementmicroservice.services.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @GetMapping("/subjects")
    public ResponseEntity<?> getAllSubjects(){
        return subjectService.getSubjects();
    }
    @PostMapping("/subject")
    public ResponseEntity<?> insertNewSubject(@RequestBody Subject subject){
        return subjectService.insertSubject(subject);
    }
    @GetMapping("/subject/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable long id){
        return subjectService.getSubjectById(id);
    }
}
