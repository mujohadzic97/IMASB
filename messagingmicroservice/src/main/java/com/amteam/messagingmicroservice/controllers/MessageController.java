package com.amteam.messagingmicroservice.controllers;

import com.amteam.messagingmicroservice.entities.MessageRequest;
import com.amteam.messagingmicroservice.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message-instructor/{instructorId}/{clientId}")
    public ResponseEntity<?> sendMessageInstructor(@RequestHeader("Authorization") String token, @PathVariable long instructorId, @PathVariable long clientId, @RequestBody MessageRequest messageRequest) {
        messageRequest.setSender("instructor");
        return messageService.createMessage(messageRequest, instructorId, clientId, token);
    }

    @PostMapping("/message-client/{instructorId}/{clientId}")
    public ResponseEntity<?> sendMessageFromClient(@RequestHeader("Authorization") String token, @PathVariable long instructorId, @PathVariable long clientId, @RequestBody MessageRequest messageRequest) {
        messageRequest.setSender("client");
        return messageService.createMessage(messageRequest, instructorId, clientId, token);
    }

    @GetMapping("messages/{instructorId}/{clientId}")
    public ResponseEntity<?> getMessageList(@PathVariable long instructorId, @PathVariable long clientId, @RequestHeader("Authorization") String token) {
        return messageService.getMessagesForUserAndClient(instructorId, clientId, token);
    }

    @GetMapping("/messages-last-month")
    public ResponseEntity<?> getMessageListFromInstructorsRegisteredLastMonth(@RequestHeader("Authorization") String token) {
        return messageService.getMessageListFromInstructorsRegisteredLastMonth(token);
    }

}
