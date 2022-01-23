package com.amteam.messagingmicroservice.repositories;

import com.amteam.messagingmicroservice.entities.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Message findById(long id);
    List<Message> findAll();
    List<Message> findByInstructorIdAndClientId(long instructor_id, long client_id);
    List<Message> findByInstructorId(long instructor_id);
}
