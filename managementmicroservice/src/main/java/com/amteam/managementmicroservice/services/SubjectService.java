package com.amteam.managementmicroservice.services;


import com.amteam.managementmicroservice.entities.ErrorMessage;
import com.amteam.managementmicroservice.entities.Subject;
import com.amteam.managementmicroservice.repositories.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ErrorMessage errorMessage = new ErrorMessage("Something went wrong!");

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public ResponseEntity<?> getSubjects(){
        try {
            List<Subject> subjects = subjectRepository.findAll();
            return new ResponseEntity<>(subjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> insertSubject(Subject subject) {
        try {
            Subject subjectsDB = subjectRepository.save(subject);
            return new ResponseEntity<>(subjectsDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getSubjectById(long id) {
        try {
            Subject subjectsDB = subjectRepository.findById(id);
            if(subjectsDB == null)
                return new ResponseEntity<>(new ErrorMessage("No subject found for provided id"),HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(subjectsDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
