package com.amteam.ratingmicroservice.repositories;

import com.amteam.ratingmicroservice.entities.Client;
import com.amteam.ratingmicroservice.entities.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Subject findById(long id);
    List<Subject> findAll();
    Subject findByName(String name);
}

