package com.amteam.requestmicroservice.repositories;

import com.amteam.requestmicroservice.entities.ClientCourse;
import com.amteam.requestmicroservice.entities.Instruction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientCourseRepository extends CrudRepository<ClientCourse, Long> {
    ClientCourse findById(long id);
    List<ClientCourse> findAll();
    List<ClientCourse> findByClientId (long  client_id);
}
