package com.room4me.dtos.property;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.room4me.entities.Property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ImageDTO {
  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;

  @NotBlank(message = "File name required")
  private String fileName;

  @NotNull(message = "Property required")
  private Property property;

  public ImageDTO() {
    super();
  }

  public ImageDTO(UUID id, String fileName,
      Property property) {
    this.id = id;
    this.fileName = fileName;
    this.property = property;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }
}
