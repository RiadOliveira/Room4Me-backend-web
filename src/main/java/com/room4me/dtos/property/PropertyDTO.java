package com.room4me.dtos.property;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.room4me.dtos.user.UserWithContactDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PropertyDTO {
  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;

  private UserWithContactDTO owner;

  @NotBlank(message = "Property title required")
  private String title;

  @NotBlank(message = "Rent period required")
  private String rentPeriod;

  @NotBlank(message = "Description required")
  private String description;

  @NotNull(message = "Initial rent date required")
  private Date initialRentDate;

  @NotNull(message = "Rent value required")
  @Positive(message = "Rent must be positive")
  private Double rent;

  private boolean availableToShare = false;

  @JsonProperty(access = Access.READ_ONLY)
  private Date createdAt;

  @JsonProperty(access = Access.READ_ONLY)
  private Date updatedAt;

  public PropertyDTO() {
    super();
  }

  public PropertyDTO(
    UUID id, String title, String rentPeriod, String description,
    Date initialRentDate, Double rent, Date createdAt,
    Date updatedAt, UserWithContactDTO owner, boolean availableToShare
  ) {
    this.id = id;
    this.title = title;
    this.rentPeriod = rentPeriod;
    this.description = description;
    this.initialRentDate = initialRentDate;
    this.rent = rent;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.owner = owner;
    this.availableToShare = availableToShare;
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

  public UserWithContactDTO getOwner() {
    return owner;
  }

  public void setOwner(UserWithContactDTO owner) {
    this.owner = owner;
  }

  public boolean isAvailableToShare() {
    return availableToShare;
  }

  public void setAvailableToShare(boolean availableToShare) {
    this.availableToShare = availableToShare;
  }
}
