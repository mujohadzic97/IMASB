package com.amteam.messagingmicroservice.entities;

import java.util.List;

public class InstructorMessages {
    private long id;
    private List<Message> messages;

    public InstructorMessages(){}
    public InstructorMessages(long id, List<Message> messages) {
        this.id = id;
        this.messages = messages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
