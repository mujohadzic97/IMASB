package com.amteam.managementmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
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
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private int role;
    @Column(name = "maxNumberOfInstructions")
    private int maxNumberOfInstructions;
    @Column(name = "numberOfScheduledInstructions")
    private int numberOfScheduledInstructions;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC")
    @Column(name = "registration_date")
    private String registrationDate;
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(name = "instructor_subjects", joinColumns = @JoinColumn(name = "instructor_id"),inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, double avgGrade, String description,
                      String userName, String password, int role, int maxNumberOfInstructions, int schInst) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.avgGrade = avgGrade;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.registrationDate = OffsetDateTime.now().toString();
        this.maxNumberOfInstructions = maxNumberOfInstructions;
        this.numberOfScheduledInstructions = schInst;
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

    public int getMaxNumberOfInstructions() {
        return maxNumberOfInstructions;
    }

    public void setMaxNumberOfInstructions(int maxNumberOfInstructions) {
        this.maxNumberOfInstructions = maxNumberOfInstructions;
    }

    public int getNumberOfScheduledInstructions() {
        return numberOfScheduledInstructions;
    }

    public void setNumberOfScheduledInstructions(int numberOfScheduledInstructions) {
        this.numberOfScheduledInstructions = numberOfScheduledInstructions;
    }

    public void addInstruction(){
        this.numberOfScheduledInstructions++;
    }

    public boolean isNotValid() {
        return avgGrade > 5 || avgGrade < 0;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }
}


