package com.amteam.requestmicroservice.Models;

import com.amteam.requestmicroservice.entities.Instructor;

import javax.persistence.*;

public class Grade {
    private String clientName;
    private String subjectName;
    private String comment;
    private int grade;

    public Grade(String clientName, String subjectName, String comment, int grade) {
        this.clientName = clientName;
        this.subjectName = subjectName;
        this.comment = comment;
        this.grade = grade;
    }
}
