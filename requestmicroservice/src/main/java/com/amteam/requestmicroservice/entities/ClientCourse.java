package com.amteam.requestmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "client_course")
public class ClientCourse {
    @Id
    @GeneratedValue(generator = "ClientCourseIdGenerator",strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "course_id")
    private Long courseId;


    public ClientCourse(long clientId, long courseId){
        this.clientId = clientId;
        this.courseId = courseId;
    }

    public ClientCourse() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
