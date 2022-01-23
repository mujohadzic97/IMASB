package com.amteam.messagingmicroservice.entities;

public class MessageRequest {
    private String message;
    private String sender;
    public MessageRequest(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isValid(){
        return message.length() <= 100;
    }

    public MessageRequest() { }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
