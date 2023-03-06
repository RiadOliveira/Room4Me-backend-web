package com.room4me.entities;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String rentPeriod;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private Date initialRentDate;
    
    @Column(nullable = false)
    private Double rent;
    private boolean availableToShare;
    
    @OneToOne(
        mappedBy = "property", cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Address address;

    @OneToOne(
        mappedBy = "property", cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Aspects aspects;

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Set<Image> images;

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Set<Question> questions;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Property() {
        super();
    }

    public Property(
        UUID id, String title, String rentPeriod,
        String description, Date initialRentDate,
        Double rent, boolean availableToShare,
        Address address, Aspects aspects, Set<Image> images,
        Set<Question> questions, User owner,
        Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.rentPeriod = rentPeriod;
        this.description = description;
        this.initialRentDate = initialRentDate;
        this.rent = rent;
        this.availableToShare = availableToShare;
        this.address = address;
        this.aspects = aspects;
        this.images = images;
        this.questions = questions;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(String rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInitialRentDate() {
        return initialRentDate;
    }

    public void setInitialRentDate(Date initialRentDate) {
        this.initialRentDate = initialRentDate;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public boolean isAvailableToShare() {
        return availableToShare;
    }

    public void setAvailableToShare(boolean availableToShare) {
        this.availableToShare = availableToShare;
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
