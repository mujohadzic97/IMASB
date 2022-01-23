package com.amteam.requestmicroservice.repositories;

import com.amteam.requestmicroservice.entities.Instruction;
import com.amteam.requestmicroservice.entities.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Subject findById(long id);
    List<Subject> findAll();

}