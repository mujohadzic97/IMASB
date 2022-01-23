package com.amteam.messagingmicroservice.entities;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "message")
    private String message;
    @Column(name = "parent_message_id")
    private Long parentMessage;
    @Column(name = "instructor_id")
    private Long instructorId;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "sender")
    private String sender;
    public Message(){}

    public Message(String message, Long parent, Long instructorId, Long clientId, String sender){
        this.date = LocalDateTime.now();
        this.message =  message;
        this.parentMessage = parent;
        this. instructorId = instructorId;
        this. clientId = clientId;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstructor() {
        return instructorId;
    }

    public void setInstructor(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Long getClient() {
        return clientId;
    }

    public void setClient(Long clientId) {
        this.clientId = clientId;
    }

    public Long getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Long parentMessage) {
        this.parentMessage = parentMessage;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
