package com.amteam.requestmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(generator = "ClientIdGenerator",strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "number_of_scheduled_inst")
    private int numberOfScheduledClasses;

    public Client() {
    }

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfScheduledClasses = 0;
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

    public int getNumberOfScheduledClasses() {return numberOfScheduledClasses;}

    public void setGetNumberOfScheduledClasses (int num) {this.numberOfScheduledClasses= num;}
}
