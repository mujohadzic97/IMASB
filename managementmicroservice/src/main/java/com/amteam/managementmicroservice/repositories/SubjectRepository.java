package com.amteam.managementmicroservice.repositories;

import com.amteam.managementmicroservice.entities.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Subject findById(long id);
    List<Subject> findAll();
    Subject findByName(String name);
}