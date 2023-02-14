package com.room4me.entities;

import java.util.Date;
import java.util.UUID;

public class Aspects {
    private UUID id;

    private int bedroomsQuantity;
    private boolean availableToShare;
    private String description; 
    private int capacity;

    private Date createdAt;
    private Date updatedAt;

    public Aspects() {
        super();
    }

    public Aspects(
        UUID id, int bedroomsQuantity,
        boolean availableToShare, String description,
        int capacity, Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.bedroomsQuantity = bedroomsQuantity;
        this.availableToShare = availableToShare;
        this.description = description;
        this.capacity = capacity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getBedroomsQuantity() {
        return bedroomsQuantity;
    }

    public void setBedroomsQuantity(int bedroomsQuantity) {
        this.bedroomsQuantity = bedroomsQuantity;
    }

    public boolean isAvailableToShare() {
        return availableToShare;
    }

    public void setAvailableToShare(boolean availableToShare) {
        this.availableToShare = availableToShare;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
