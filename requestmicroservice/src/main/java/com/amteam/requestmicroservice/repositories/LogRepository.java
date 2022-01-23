package com.amteam.requestmicroservice.repositories;

import com.amteam.requestmicroservice.entities.LogInDB;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogRepository extends CrudRepository<LogInDB, Long> {
    LogInDB findById(long id);
    List<LogInDB> findAll();
}
