package com.amteam.managementmicroservice.entities;

import com.amteam.managementmicroservice.interfaces.IManagemenrServicePublish;
import com.amteam.managementmicroservice.repositories.InstructorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementServicePublisher implements IManagemenrServicePublish {
    private final RabbitTemplate rabbitTemplate;
    private final InstructorRepository instructorRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public ManagementServicePublisher(RabbitTemplate rabbitTemplate, InstructorRepository instructorRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.instructorRepository = instructorRepository;
    }


    @Override
    public void sendInstructionAdded(Instructor inst, InstructorInstructions instructorInstructions) {
        try{
            if (inst.getMaxNumberOfInstructions() >= instructorInstructions.numberOfScheduledInstructions + 1) {
                Instructor instructorDB = instructorRepository.findById(instructorInstructions.instructorID.intValue());
                instructorDB.addInstruction();
                instructorRepository.save(instructorDB);
                rabbitTemplate.convertAndSend("dodana-instrukcija-callback", "management.request", objectMapper.writeValueAsString(instructorInstructions));
            }
            else {
                rabbitTemplate.convertAndSend("dodana-instrukcija-callback", "management.request", objectMapper.writeValueAsString(instructorInstructions));
            }
        } catch(Exception ex){

        }
    }
}
