package com.amteam.requestmicroservice.Models;

import com.amteam.requestmicroservice.entities.Instructor;

import javax.persistence.*;

public class Comment {
    private String comment;
    private int grade;

    public Comment(String comment, int grade) {
        this.comment = comment;
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
