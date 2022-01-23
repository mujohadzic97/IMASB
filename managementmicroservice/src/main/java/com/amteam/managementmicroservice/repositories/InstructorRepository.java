package com.amteam.managementmicroservice.repositories;

import com.amteam.managementmicroservice.entities.Instructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
    Instructor findById(long id);
    List<Instructor> findAll();
    Instructor findByUserName(String userName);
}