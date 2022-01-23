package com.amteam.ratingmicroservice.repositories;

import com.amteam.ratingmicroservice.entities.Instructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
    Instructor findById(long id);
    List<Instructor> findAll();
    Instructor findByUserName(String userName);
}