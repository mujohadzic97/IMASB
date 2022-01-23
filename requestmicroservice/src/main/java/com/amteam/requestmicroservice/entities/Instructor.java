package com.amteam.requestmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Instructor")
public class Instructor {
    @Id
    @GeneratedValue(generator = "InstructorIdGenerator",strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "avg_grade")
    private double avgGrade;

    @Column(name = "description")
    private String description;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(name = "instructor_subjects", joinColumns = @JoinColumn(name = "instructor_id"),inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();;

    @Column(name = "registration_date")
    private String registrationDate;

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, double avgGrade, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.avgGrade = avgGrade;
        this.registrationDate = OffsetDateTime.now().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }
}

