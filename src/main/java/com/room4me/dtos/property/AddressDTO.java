package com.room4me.dtos.property;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class AddressDTO {
  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;

  @NotBlank(message = "Country required")
  private String country;

  @NotBlank(message = "State required")
  private String state;

  @NotBlank(message = "City required")
  private String city;

  @NotBlank(message = "District required")
  private String district;

  @NotBlank(message = "Street required")
  private String street;

  @NotBlank(message = "Property number required")
  private String propertyNumber;

  private String condominiumNumber;
  private String block;
  private String complement;

  @Positive(message = "Zip code must be positive")
  private Integer zipCode;

  @JsonProperty(access = Access.READ_ONLY)
  private Date createdAt;

  @JsonProperty(access = Access.READ_ONLY)
  private Date updatedAt;

  public AddressDTO() {
    super();
  }

  public AddressDTO(UUID id, String country,
      String state, String city, String district, String street,
      String propertyNumber, String condominiumNumber, String block,
      String complement, Integer zipCode, Date createdAt,
      Date updatedAt) {
    this.id = id;
    this.country = country;
    this.state = state;
    this.city = city;
    this.district = district;
    this.street = street;
    this.propertyNumber = propertyNumber;
    this.condominiumNumber = condominiumNumber;
    this.block = block;
    this.complement = complement;
    this.zipCode = zipCode;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPropertyNumber() {
    return propertyNumber;
  }

  public void setPropertyNumber(String propertyNumber) {
    this.propertyNumber = propertyNumber;
  }

  public String getCondominiumNumber() {
    return condominiumNumber;
  }

  public void setCondominiumNumber(String condominiumNumber) {
    this.condominiumNumber = condominiumNumber;
  }

  public String getBlock() {
    return block;
  }

  public void setBlock(String block) {
    this.block = block;
  }

  public String getComplement() {
    return complement;
  }

  public void setComplement(String complement) {
    this.complement = complement;
  }

  public Integer getZipCode() {
    return zipCode;
  }

  public void setZipCode(Integer zipCode) {
    this.zipCode = zipCode;
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
