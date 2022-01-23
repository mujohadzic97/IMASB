package com.amteam.requestmicroservice.Models;

import com.amteam.requestmicroservice.interfaces.IRequestServicePublish;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServicePublisher implements IRequestServicePublish {

    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public RequestServicePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendInstructionAdded(int numberOfInstructionsForInstructor, Long instructorId, Long savedInstructionId) {
        try {
            InstructorInstructions DTO = new InstructorInstructions(numberOfInstructionsForInstructor, instructorId, savedInstructionId);
            rabbitTemplate.convertAndSend("nova-instrukcija", "request.management", objectMapper.writeValueAsString(DTO));
        } catch (Exception ex){

        }
    }
}
