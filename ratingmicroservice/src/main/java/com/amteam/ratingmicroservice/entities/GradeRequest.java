package com.amteam.ratingmicroservice.entities;

public class GradeRequest {


    private int grade;
    private String sender;
    private String graded;
    private String comment;

    public GradeRequest(int grade, String graded, String comment) {
        this.grade = grade;
        this.graded = graded;
        this.comment = comment;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public GradeRequest() { }

    public int getGrade() {
        return grade;
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
}
