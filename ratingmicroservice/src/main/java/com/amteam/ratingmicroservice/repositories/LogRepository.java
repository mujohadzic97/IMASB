package com.amteam.ratingmicroservice.repositories;

import com.amteam.ratingmicroservice.entities.LogInDB;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogRepository extends CrudRepository<LogInDB, Long> {
    LogInDB findById(long id);
    List<LogInDB> findAll();
}
