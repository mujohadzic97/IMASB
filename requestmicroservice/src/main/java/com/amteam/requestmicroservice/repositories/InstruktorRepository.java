package com.amteam.requestmicroservice.repositories;

import com.amteam.requestmicroservice.entities.Instructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstruktorRepository extends CrudRepository<Instructor, Long> {
    Instructor findById(long id);
    List<Instructor> findAll();

}
