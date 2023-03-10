package com.room4me.dtos.property;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.Positive;

public class AspectsDTO {
  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;

  private boolean shareWithSameGender = false;
  private boolean acceptAnimals = false;
  private boolean hasGarage = false;
  private boolean furnished = false;

  @Positive(message = "Bathrooms quantity must be positive")
  private Integer bathroomsQuantity;

  @Positive(message = "Bedrooms quantity must be positive")
  private Integer bedroomsQuantity;

  @JsonProperty(access = Access.READ_ONLY)
  private Date createdAt;

  @JsonProperty(access = Access.READ_ONLY)
  private Date updatedAt;

  public AspectsDTO() {
    super();
  }

  public AspectsDTO(
    UUID id, boolean shareWithSameGender,
    boolean acceptAnimals, boolean hasGarage, boolean furnished,
    Integer bathroomsQuantity, Integer bedroomsQuantity,
    Date createdAt, Date updatedAt
  ) {
    this.id = id;
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

  public Integer getBedroomsQuantity() {
    return bedroomsQuantity;
  }

  public void setBedroomsQuantity(Integer bedroomsQuantity) {
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
