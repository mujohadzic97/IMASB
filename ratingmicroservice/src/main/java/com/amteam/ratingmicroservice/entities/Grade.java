package com.amteam.ratingmicroservice.entities;

import javax.persistence.*;

@Entity
@Table(name = "Grade")
public class Grade {
    @Id
    @GeneratedValue(generator = "GradeIdGenerator",strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "instructor_id")
    private Long instructorId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "graded")
    private String graded;

    @Column(name = "grade")
    private int grade;


    public Grade() {
    }

    public Grade(String comment, int grade, Long instructor, Long client, Long subject, String graded) {
        this.comment = comment;
        this.grade = grade;
        this.clientId = client;
        this.instructorId = instructor;
        this.subjectId = subject;
        this.graded = graded;
    }
    public Grade(String comment, int grade, int instructor, int client, int subject, String graded) {
        this.comment = comment;
        this.grade = grade;
        this.clientId = (long) client;
        this.instructorId = (long) instructor;
        this.subjectId = (long) subject;
        this.graded = graded;
    }

    public Long getInstructor_id() {
        return instructorId;
    }

    public void setInstructor_id(Long instructor_id) {
        this.instructorId = instructor_id;
    }

    public Long getClient_id() {
        return clientId;
    }

    public void setClient_id(Long client_id) {
        this.clientId = client_id;
    }

    public Long getSubject() {
        return subjectId;
    }

    public void setSubject(Long subject) {
        this.subjectId = subject;
    }

    public int getGrade() {
        return grade;
    }

    public Long getId() {
        return id;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGraded() {
        return graded;
    }

    public void setGraded(String graded) {
        this.graded = graded;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean validateGrade(){
        return grade > 0 && grade <= 5 && clientId != null && instructorId != null && subjectId != null;
    }
}
