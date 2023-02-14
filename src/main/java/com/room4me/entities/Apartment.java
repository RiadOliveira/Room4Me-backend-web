package com.room4me.entities;

import java.util.Date;
import java.util.UUID;

public class Apartment {
    private UUID id;
    private Address address;
    private Aspects aspects;
    
    private double rent;
    private User owner;
    private String image;

    private Date createdAt;
    private Date updatedAt;

    public Apartment() {
        super();
    }

    public Apartment(
        UUID id, Address address,
        Aspects aspects, double rent,
        User owner, String image,
        Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.address = address;
        this.aspects = aspects;
        this.rent = rent;
        this.owner = owner;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Aspects getAspects() {
        return aspects;
    }

    public void setAspects(Aspects aspects) {
        this.aspects = aspects;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    } 
}
