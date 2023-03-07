package com.room4me.dtos;

import java.util.Date;
import java.util.UUID;

public class FavoritePropertyDTO {
    private UUID id;
    private UUID propertyId;

    private Date createdAt;
    private Date updatedAt;

    public FavoritePropertyDTO() {
        super();
    }

    public FavoritePropertyDTO(
        UUID id, UUID propertyId,
        Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.propertyId = propertyId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(UUID propertyId) {
        this.propertyId = propertyId;
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
