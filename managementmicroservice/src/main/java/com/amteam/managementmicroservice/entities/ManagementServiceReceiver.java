package com.amteam.managementmicroservice.entities;

import com.amteam.managementmicroservice.interfaces.IManagementServiceReceiver;
import com.amteam.managementmicroservice.repositories.InstructorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues  = "instrukcije")
public class ManagementServiceReceiver implements IManagementServiceReceiver {
    private final InstructorRepository instructorRepository;
    private final ManagementServicePublisher publisher;
    @Autowired
    private ObjectMapper objectMapper;

    public ManagementServiceReceiver(RabbitTemplate rabbitTemplate, InstructorRepository instructorService, ManagementServicePublisher publisher) {
        this.instructorRepository = instructorService;
        this.publisher = publisher;
    }

    @RabbitHandler
    public void receive(String content) {
        try {
            InstructorInstructions instructorInstructions = objectMapper.readValue(content, InstructorInstructions.class);
            Instructor inst = instructorRepository.findById(instructorInstructions.instructorID.intValue());
            publisher.sendInstructionAdded(inst, instructorInstructions );
        } catch (IOException ex) {
        }
    }
}
