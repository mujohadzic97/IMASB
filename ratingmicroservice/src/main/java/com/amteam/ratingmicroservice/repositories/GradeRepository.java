package com.amteam.ratingmicroservice.repositories;

import com.amteam.ratingmicroservice.entities.Grade;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GradeRepository extends CrudRepository<Grade, Long> {
    Grade findById(long id);
    List<Grade> findAll();
    List<Grade> findByInstructorIdAndClientIdAndSubjectId(long instructor_id, long client_id, long subject_id);
    List<Grade> findByInstructorIdAndSubjectId(long instructor_id, long subject_id);
    List<Grade> findBySubjectId(long subject_id);
    List<Grade> findByInstructorId(long instructor_id);
}

