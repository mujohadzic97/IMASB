package com.amteam.managementmicroservice.services;

import com.amteam.managementmicroservice.entities.ErrorMessage;
import com.amteam.managementmicroservice.entities.Instructor;
import com.amteam.managementmicroservice.repositories.InstructorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final ErrorMessage errorMessage =  new ErrorMessage("Something went wrong!");

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public ResponseEntity<?> getInstructorList() {
        try {

            List<Instructor> instructorList = instructorRepository.findAll();
            return new ResponseEntity<>(instructorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getInstructorById(int instructorId) {
        try {
            Instructor instructor = instructorRepository.findById(instructorId);
            if(instructor == null)
                return new ResponseEntity<>(new ErrorMessage("No user found!"),HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(instructor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> insertNewInstructor(Instructor instructor) {
        try {
            if(instructor.isNotValid())
                return new ResponseEntity<>( new ErrorMessage("Average grade can't be higher 5 or lower then 0!"), HttpStatus.BAD_REQUEST);
            if(instructorRepository.findByUserName(instructor.getUserName())!=null)
                return new ResponseEntity<>( new ErrorMessage("User with this username already exists"),HttpStatus.BAD_REQUEST);
            instructor.setRegistrationDate(OffsetDateTime.now().toString());
            instructor.setRole(2);
            Instructor instructorDB = instructorRepository.save(instructor);
            return new ResponseEntity<>(instructorDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> correctAvgGrade(Instructor instructor) {
        try {

            if(instructor.isNotValid())
                return new ResponseEntity<>( new ErrorMessage("Average grade can't be higher 5 or lower then 0!"), HttpStatus.BAD_REQUEST);
            if(instructorRepository.findByUserName(instructor.getUserName()) == null)
                return new ResponseEntity<>( new ErrorMessage("User with this username does not exist"),HttpStatus.BAD_REQUEST);

            Instructor instructorDB = instructorRepository.findByUserName(instructor.getUserName());
            System.out.println(instructor.getAvgGrade());
            //instructorRepository.delete(instructorDB);
            instructorDB.setAvgGrade(instructor.getAvgGrade());
            instructorDB = instructorRepository.save(instructorDB);

            return new ResponseEntity<>(instructorDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);


        }

    }

    public ResponseEntity<?> getRegisteredLastMonth() {
        try {
            List<Instructor> instructorList = instructorRepository.findAll();
            if(instructorList==null || instructorList.size() ==0)
                return new ResponseEntity<>("No instructors were registered last month",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(instructorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}