package com.amteam.requestmicroservice.controllers;

import com.amteam.requestmicroservice.entities.Client;
import com.amteam.requestmicroservice.entities.ClientCourse;
import com.amteam.requestmicroservice.entities.Course;
import com.amteam.requestmicroservice.entities.ErrorMessage;
import com.amteam.requestmicroservice.repositories.ClientCourseRepository;
import com.amteam.requestmicroservice.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final ClientCourseRepository clientCourseRepository;
    private final CourseRepository courseRepository;
    public CourseController(ClientCourseRepository clientCourseRepository, CourseRepository courseRepository) {
        this.clientCourseRepository = clientCourseRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/get-courses")
    public ResponseEntity<?> getAllCourses(){
        try {
            List<Course> clientCourseList = courseRepository.findAll();
            return new ResponseEntity<>(clientCourseList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong",e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-course/{courseId}")
    public ResponseEntity<?> getCourseId(@PathVariable long courseId){
        try {
            Course course = courseRepository.findById(courseId);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong",e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-course-instructor/{instructorId}")
    public ResponseEntity<?> getAllCoursesHeldByThisInstructor(@PathVariable long instructorId){
        try {
            List<Course> courseList = courseRepository.findByInstructorId(instructorId);
            return new ResponseEntity<>(courseList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong",e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-course-client/{clientId}")
    public ResponseEntity<?> getAllCoursesForThisClient(@PathVariable long clientId){
        try {
            List<ClientCourse> clientCourseList = clientCourseRepository.findByClientId(clientId);
            return new ResponseEntity<>(clientCourseList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong",e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create-course")
    public ResponseEntity<?> createCourse(@RequestBody Course course){
        try {
            System.out.println("Dodem");
            Course courseDB = courseRepository.save(course);

            System.out.println("Prodem");
            return new ResponseEntity<>(courseDB, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong",e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/enrol-into-course/{courseId}/{clientId}")
    public ResponseEntity<?> enrolClientIntoCourse(@PathVariable long courseId, @PathVariable long clientId){
        try {
            Course courseDB = courseRepository.findById(courseId);
            if(courseDB.getMaxCapacity() == courseDB.getCurrentCapacity())
                return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST, "Bad Request","Course already full"), HttpStatus.BAD_REQUEST);
            courseDB.setCurrentCapacity(courseDB.getCurrentCapacity() + 1);
            Course savedCourse = courseRepository.save(courseDB);
            ClientCourse clientCourse = new ClientCourse(clientId,courseId);
            ClientCourse clientCourseDB = clientCourseRepository.save(clientCourse);
            return new ResponseEntity<>(savedCourse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong",e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
