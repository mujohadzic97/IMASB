package com.amteam.requestmicroservice.Models;

import com.amteam.requestmicroservice.entities.Instruction;
import com.amteam.requestmicroservice.interfaces.IRequestServiceReceiver;
import com.amteam.requestmicroservice.repositories.InstrukcijaReposiotry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues  = "instrukcije-callback")
public class RequestServiceReceiver implements IRequestServiceReceiver{
    private final RabbitTemplate rabbitTemplate;
    private final InstrukcijaReposiotry instructionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public RequestServiceReceiver(RabbitTemplate rabbitTemplate, InstrukcijaReposiotry instructionReposiotry) {
        this.rabbitTemplate = rabbitTemplate;
        this.instructionRepository = instructionReposiotry;
    }

    @RabbitHandler
    public void receive(String content) {
        try {
            InstructorInstructions instructorInstructions = objectMapper.readValue(content, InstructorInstructions.class);
            int numberOfInstructionsForInstructor = instructionRepository.findByInstructorId(instructorInstructions.getInstructorID()).size();
            //ako nije mogao dodati u management obrisi iz baze (salje se broj inst prije dodavanja i poveca se jedino ako moze prema podacima u management servisu)
            if (instructorInstructions.getNumberOfScheduledInstructions() != numberOfInstructionsForInstructor) {
                Instruction instructionFromDB = instructionRepository.findById(instructorInstructions.getInstructionID().intValue());
                //ako instrukcija nije dodana uspjesno u management onda se postavlja da ije aktivna
                instructionFromDB.setActive(false);
                instructionRepository.save(instructionFromDB);
            }
        } catch (IOException ex) {

        }
    }
}
