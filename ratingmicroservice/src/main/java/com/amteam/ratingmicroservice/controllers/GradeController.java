package com.amteam.ratingmicroservice.controllers;

import com.amteam.ratingmicroservice.entities.Grade;
import com.amteam.ratingmicroservice.entities.Client;
//import com.amteam.ratingmicroservice.repositories.GradeRepository;
import com.amteam.ratingmicroservice.entities.GradeRequest;
import com.amteam.ratingmicroservice.repositories.ClientRepository;
import com.amteam.ratingmicroservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@RestController
public class GradeController {
    private final RatingService ratingService;

    @Value("${service.management.serviceId}")
    private String clientSearchServiceId;

    public GradeController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/grade-client/{instructorId}/{clientId}/{subjectId}")
    public ResponseEntity<?> gradeInstructor(@RequestHeader("Authorization") String token, @PathVariable long instructorId, @PathVariable long clientId, @PathVariable long subjectId,@RequestBody GradeRequest gradeRequest){
        gradeRequest.setSender("instructor");
        gradeRequest.setGraded("client");
        return ratingService.createGrade(gradeRequest,instructorId,clientId,subjectId,token);
    }
    @PostMapping("/grade-instructor/{instructorId}/{clientId}/{subjectId}")
    public ResponseEntity<?> gradeClient(@RequestHeader("Authorization") String token, @PathVariable long instructorId, @PathVariable long clientId, @PathVariable long subjectId,@RequestBody GradeRequest gradeRequest){
        gradeRequest.setSender("client");
        gradeRequest.setGraded("instructor");
        return ratingService.createGrade(gradeRequest,instructorId,clientId,subjectId,token);
    }
    @GetMapping("/grades-all")
    public ResponseEntity<?> getGradeList(){
        return ratingService.getGrades();
    }
    @GetMapping("/grades-specific/{instructorId}/{clientId}/{subjectId}")
    public ResponseEntity<?> gradeGradeListSpecific(@PathVariable long instructorId, @PathVariable long clientId, @PathVariable long subjectId){
        return ratingService.getGradesSpecific(instructorId,clientId,subjectId);
    }
    @GetMapping("/grades-instructor/{instructorId}/{subjectId}")
    public ResponseEntity<?> gradeGradeListInstructor(@PathVariable long instructorId, @PathVariable long subjectId){
        return ratingService.getGradesInstructor(instructorId,subjectId);
    }
    @GetMapping("/grades-instructor-all/{instructorId}")
    public ResponseEntity<?> gradeGradeListInstructor(@PathVariable long instructorId){
        return ratingService.getGradesInstructorAll(instructorId);
    }
    @GetMapping("/grades-top5/{subjectId}")
    public ResponseEntity<?> gradeInstructorListTop5(@RequestHeader("Authorization") String token, @PathVariable long subjectId){
        return ratingService.getTop5(subjectId,token);
    }
    @GetMapping("/grades-top5-all")
    public ResponseEntity<?> gradeInstructorListTop5All(@RequestHeader("Authorization") String token){
        return ratingService.getTop5All(token);
    }

    @GetMapping("/grades-comments/{instructorId}")
    public ResponseEntity<?> gradeInstructorListTop5All(@RequestHeader("Authorization") String token, @PathVariable long instructorId){
        return ratingService.getComments(instructorId,token);
    }
}

















/*

    private final GradeRepository gradeRepository;
    //private final ClientRepository clientRepository;

    public GradeController(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @PostMapping(value = "/grade")
    public ResponseEntity<?> saveGrade(@RequestBody Grade grade){
        if(!grade.validateGrade())
            return new ResponseEntity<>("Neispravni podaci za ocjenu!", HttpStatus.BAD_REQUEST);
        List<Client> clientList = clientRepository.findAll();
        Grade gradeDB = gradeRepository.findBy(subject.getName());
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


    @GetMapping(value = "/grades")
    public ResponseEntity<?> getAllGrades(){
        List<Grade> gradeList = gradeRepository.findAll();
        if(gradeList == null || gradeList.size() == 0)
            return new ResponseEntity<>("Nije pronađena lista ocjena!", HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(gradeList, HttpStatus.OK);
    }


}
*/
