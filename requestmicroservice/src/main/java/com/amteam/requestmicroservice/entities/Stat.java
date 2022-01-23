package com.amteam.requestmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class Stat {
    @JsonProperty
    private Long subjectId;
    @JsonProperty
    private String subjectName;
    @JsonProperty
    private String subjectDescription;
    @JsonProperty
    private int numberOfInstructions;
    @JsonProperty
    private int numberOfCourses;

    public Stat(){

    }

    public Stat(Long subjectId, String subjectName, String subjectDescription, int numberOfInstructions, int numberOfCourses) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.numberOfInstructions = numberOfInstructions;
        this.numberOfCourses = numberOfCourses;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public int getNumberOfInstructions() {
        return numberOfInstructions;
    }

    public void setNumberOfInstructions(int numberOfInstructions) {
        this.numberOfInstructions = numberOfInstructions;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(int numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }
}
