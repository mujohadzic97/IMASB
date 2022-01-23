package com.amteam.requestmicroservice.controllers;


import com.amteam.requestmicroservice.entities.ClassRooms;
import com.amteam.requestmicroservice.entities.Course;
import com.amteam.requestmicroservice.entities.ErrorMessage;
import com.amteam.requestmicroservice.repositories.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassRoomController {
    private final ClassRoomRepository classRoomRepository;

    public ClassRoomController(ClassRoomRepository classRoomRepository) {
        this.classRoomRepository = classRoomRepository;
    }
    @GetMapping("/get-rooms")
    public ResponseEntity<?> getAllClassRooms(){
        try {
            List<ClassRooms> classRoomsList = classRoomRepository.findAll();
            return new ResponseEntity<>(classRoomsList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong",e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-room/{id}")
    public ResponseEntity<?> getAllClassRoomId(@PathVariable long id){
        try {
            ClassRooms classRoomsList = classRoomRepository.findById(id);
            return new ResponseEntity<>(classRoomsList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong",e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
