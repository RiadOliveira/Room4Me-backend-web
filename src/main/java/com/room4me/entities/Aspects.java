package com.room4me.entities;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aspects")
public class Aspects {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    private Property property;

    private boolean shareWithSameGender;
    private boolean acceptAnimals;
    private boolean hasGarage;
    private boolean furnished;

    private Integer bathroomsQuantity;
    private Integer bedroomsQuantity;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Aspects() {
        super();
    }

    public Aspects(
        UUID id, Property property,
        boolean shareWithSameGender, boolean acceptAnimals,
        boolean hasGarage, boolean furnished,
        Integer bathroomsQuantity, Integer bedroomsQuantity,
        Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.property = property;
        this.shareWithSameGender = shareWithSameGender;
        this.acceptAnimals = acceptAnimals;
        this.hasGarage = hasGarage;
        this.furnished = furnished;
        this.bathroomsQuantity = bathroomsQuantity;
        this.bedroomsQuantity = bedroomsQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public boolean isShareWithSameGender() {
        return shareWithSameGender;
    }

    public void setShareWithSameGender(boolean shareWithSameGender) {
        this.shareWithSameGender = shareWithSameGender;
    }

    public boolean isAcceptAnimals() {
        return acceptAnimals;
    }

    public void setAcceptAnimals(boolean acceptAnimals) {
        this.acceptAnimals = acceptAnimals;
    }

    public boolean isHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public Integer getBathroomsQuantity() {
        return bathroomsQuantity;
    }

    public void setBathroomsQuantity(Integer bathroomsQuantity) {
        this.bathroomsQuantity = bathroomsQuantity;
    }

    public int getBedroomsQuantity() {
        return bedroomsQuantity;
    }

    public void setBedroomsQuantity(int bedroomsQuantity) {
        this.bedroomsQuantity = bedroomsQuantity;
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
