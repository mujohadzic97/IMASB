package com.amteam.messagingmicroservice.entities;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "Instructor")
public class Instructor {
    @Id
    @GeneratedValue(generator = "InstructorIdGenerator", strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "avg_grade")
    private double avgGrade;
    @Column(name = "description")
    private String description;
    @Column(name = "subject")
    private String subject;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private int role;
    @Column(name = "registration_date")
    private String registrationDate;
    public Instructor() {
    }

    public Instructor(String firstName, String lastName, double avgGrade, String description, String subject,
                      String userName, String password, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.subject = subject;
        this.avgGrade = avgGrade;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.registrationDate = OffsetDateTime.now().toString();
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isNotValid() {
        return avgGrade > 5 || avgGrade < 0;
    }
}


