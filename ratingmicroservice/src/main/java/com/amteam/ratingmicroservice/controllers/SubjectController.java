package com.amteam.ratingmicroservice.controllers;

import com.amteam.ratingmicroservice.entities.Subject;
import com.amteam.ratingmicroservice.repositories.SubjectRepository;
import com.amteam.ratingmicroservice.services.SubjectService;
import com.amteam.ratingmicroservice.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubjectController {

    private final SubjectService subjectService;
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping(value = "/subject")
    public ResponseEntity<?> saveSubject(@RequestBody Subject subject){
            return subjectService.insertNewSubject(subject);
    }
    @GetMapping(value = "/subjects")
    public ResponseEntity<?> getAllSubjects(){
        return subjectService.getSubjects();
    }

}
