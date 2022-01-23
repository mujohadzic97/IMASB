package com.amteam.requestmicroservice.repositories;

import com.amteam.requestmicroservice.entities.ClientCourse;
import com.amteam.requestmicroservice.entities.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findById(long id);
    List<Course> findAll();
    List<Course> findByInstructorId (long  instructor_id);
    List<Course> findBySubjectId (long  subject_id);
}
