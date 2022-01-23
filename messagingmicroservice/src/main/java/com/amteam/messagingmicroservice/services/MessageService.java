package com.amteam.messagingmicroservice.services;

import com.amteam.messagingmicroservice.entities.*;
import com.amteam.messagingmicroservice.repositories.MessageRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    @Value("${service.management.serviceId}")
    private String employeeSearchServiceId;
    private final ErrorMessage errorMessage = new ErrorMessage("Something went wrong!");

    public MessageService(MessageRepository messageRepository, RestTemplate restTemplate, EurekaClient eurekaClient) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
    }

    public Instructor getInst(Long id, String token) throws Exception {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructor/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service","Messaging microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Instructor> instructorResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Instructor.class);
        return instructorResponseEntity.getBody();
    }

    public Client getClient(Long id, String token) throws Exception {
        Application application = eurekaClient.getApplication(employeeSearchServiceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/client/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("Service","Messaging microservice");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Client> clientResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Client.class);
        return clientResponseEntity.getBody();
    }

    public ResponseEntity<?> createMessage(MessageRequest messageRequest, Long instructorId, Long clientId, String token) {
        try {
            if (!messageRequest.isValid())
                return new ResponseEntity<>(new ErrorMessage("Message length can't be over 100 characters!"), HttpStatus.BAD_REQUEST);
            Client client = getClient(clientId, token);
            Instructor instructor = getInst(instructorId, token);
            if (client == null || instructor == null) {
                return new ResponseEntity<>(new ErrorMessage("Instructor or client with provided id does not exist"), HttpStatus.NOT_FOUND);
            }
            List<Message> messageList = messageRepository.findByInstructorIdAndClientId(instructorId, clientId);
            if (messageList.size() == 0) {
                Message message = new Message(messageRequest.getMessage(), null, instructor.getId(), client.getId(), messageRequest.getSender());
                Message messageDB = messageRepository.save(message);
                return new ResponseEntity<>(messageDB, HttpStatus.OK);
            } else {
                Message parentMessage = messageList.get(0);
                for (Message lastMessage : messageList) {
                    if (parentMessage == null) {
                        parentMessage = lastMessage;
                    } else {
                        if (lastMessage.getId() >= parentMessage.getId()) {
                            parentMessage = lastMessage;
                        }
                    }
                }
                Message message = new Message(messageRequest.getMessage(), parentMessage.getId(), instructor.getId(), client.getId(), messageRequest.getSender());
                Message messageDB = messageRepository.save(message);
                return new ResponseEntity<>(messageDB, HttpStatus.OK);
            }
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException)
                return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getMessagesForUserAndClient(Long instructorId, Long clientId, String token) {
        try {

            Client client = getClient(clientId, token);
            Instructor instructor = getInst(instructorId, token);
            if (client == null || instructor == null) {
                return new ResponseEntity<>(new ErrorMessage("Instructor or client with provided id does not exist"), HttpStatus.NOT_FOUND);
            }
            List<Message> messageList = messageRepository.findByInstructorIdAndClientId(instructorId, clientId);
            if (messageList == null || messageList.size() == 0) {
                return new ResponseEntity<>(new ErrorMessage("Client and Instructor have no messages!"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(messageList, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException)
                return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
            System.out.println(e.getMessage());
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<Message> getMessageListForInstructor(long id) throws Exception {
        return messageRepository.findByInstructorId(id);
    }

    public ResponseEntity<?> getMessageListFromInstructorsRegisteredLastMonth(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            Application application = eurekaClient.getApplication(employeeSearchServiceId);
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/instructors-registered-last-month";
            ResponseEntity<Instructor[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Instructor[].class);
            List<Instructor> instructorList = Arrays.asList(response.getBody());
            List<InstructorMessages> instructorMessagesList = new ArrayList<>();
            for (Instructor instructor : instructorList) {
                List<Message> messageList = getMessageListForInstructor(instructor.getId());
                if (messageList != null && messageList.size() != 0)
                    instructorMessagesList.add(new InstructorMessages(instructor.getId(), messageList));
            }
            return new ResponseEntity<>(instructorMessagesList, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException)
                return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
