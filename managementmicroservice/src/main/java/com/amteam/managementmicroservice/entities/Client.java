package com.amteam.managementmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private int role;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "UTC")
    @Column(name = "registration_date")
    private String registrationDate;
    public Client() {
    }

    public Client(String firstName, String lastName, String password, String userName, int role) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public boolean isNotValid() {
        return password.matches("[^0-9]+");
    }
}
