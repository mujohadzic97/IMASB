package com.amteam.requestmicroservice.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "class_room")
public class ClassRooms {
    @Id
    @GeneratedValue(generator = "RoomIdGenerator",strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "address")
    private String address;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "monthly_price")
    private String monthlyPrice;
    @Column(name = "rented")
    private boolean rented;

    public ClassRooms() {
    }

    public ClassRooms(String address, int capacity, String monthlyPrice, boolean rented) {
        this.address = address;
        this.capacity = capacity;
        this.monthlyPrice = monthlyPrice;
        this.rented = rented;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(String monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rentedA) {
        rented = rentedA;
    }
}
