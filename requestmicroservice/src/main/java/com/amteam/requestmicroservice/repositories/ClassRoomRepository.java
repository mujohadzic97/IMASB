package com.amteam.requestmicroservice.repositories;

import com.amteam.requestmicroservice.entities.ClassRooms;
import com.amteam.requestmicroservice.entities.ClientCourse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClassRoomRepository extends CrudRepository<ClassRooms, Long> {
    ClassRooms findById(long id);
    List<ClassRooms> findAll();
}
