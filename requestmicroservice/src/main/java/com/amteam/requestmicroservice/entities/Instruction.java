package com.amteam.requestmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;


@Entity
@Table(name = "Instruction")
public class Instruction {

    @Id
    @GeneratedValue( generator = "InstructionIdGenerator",strategy = GenerationType.AUTO)
    private Long id;


    @JoinColumn(name = "subject_id")
    private Long subjectId;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC")
    @Column(name = "scheduledDate")
    private OffsetDateTime scheduledDate;

    @Column(name = "numberOfClasses")
    private Integer numberOfClasses;

    @JoinColumn(name = "instructor_id")
    private Long instructorId;

    @JoinColumn(name = "client_id")
    private Long clientId;

    @JoinColumn(name ="active")
    private boolean active;

    public Instruction() {
    }

    public Instruction (Long subject, OffsetDateTime scheduledDate, int numberOfClasses, Long instructor, Long client){
        this.subjectId = subject;
        this.scheduledDate = scheduledDate;
        this.numberOfClasses = numberOfClasses;
        this.instructorId = instructor;
        this.clientId = client;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(Integer numberOfClasses) {
         this.numberOfClasses = numberOfClasses;
    }

    public OffsetDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(OffsetDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
