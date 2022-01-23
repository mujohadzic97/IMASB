package com.amteam.managementmicroservice.repositories;

import com.amteam.managementmicroservice.entities.LogInDB;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogRepository extends CrudRepository<LogInDB, Long> {
    LogInDB findById(long id);
    List<LogInDB> findAll();
}
