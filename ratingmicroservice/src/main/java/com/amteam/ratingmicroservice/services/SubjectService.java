package com.amteam.ratingmicroservice.services;

import com.amteam.ratingmicroservice.entities.Subject;
import com.amteam.ratingmicroservice.repositories.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public ResponseEntity<?> insertNewSubject(@RequestBody Subject subject){
        //if(!subject.validateSubject())
        //    return new ResponseEntity<>("Neispravni parametri za predmet!", HttpStatus.BAD_REQUEST);
        Subject subjectDB = subjectRepository.findByName(subject.getName());
        if(subjectDB != null)
            return new ResponseEntity<>("Postoji predmet sa datim nazivom!", HttpStatus.BAD_REQUEST);
        else{
            try {
                subjectDB = subjectRepository.save(subject);
                return new ResponseEntity<>(subjectDB, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>("Greška na serveru!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    public ResponseEntity<?> getSubjects(){
        List<Subject> subjectList = subjectRepository.findAll();
        if(subjectList == null || subjectList.size() == 0)
            return new ResponseEntity<>("Nije pronađena lista predmeta!", HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(subjectList, HttpStatus.OK);
    }
}
