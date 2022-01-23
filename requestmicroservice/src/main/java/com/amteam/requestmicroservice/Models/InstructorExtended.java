package com.amteam.requestmicroservice.Models;

import com.amteam.requestmicroservice.entities.Subject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InstructorExtended {

    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private double avgGrade;
    @JsonProperty
    private String description;
    @JsonProperty
    private int numberOfScheduledClasses;
    @JsonProperty
    List<Subject> subjects;
    @JsonProperty
    List<Comment>  comments;

    public InstructorExtended(){}

    public InstructorExtended(String firstName, String lastName, double avgGrade, String description, List<Subject> subjects, List<Comment> comments, int numberOfScheduledClasses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.avgGrade = avgGrade;
        this.description = description;
        this.subjects = subjects;
        this.comments = comments;
        this.numberOfScheduledClasses =numberOfScheduledClasses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfScheduledClasses() {
        return numberOfScheduledClasses;
    }

    public void setNumberOfScheduledClasses(int numberOfScheduledClasses) {
        this.numberOfScheduledClasses = numberOfScheduledClasses;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
