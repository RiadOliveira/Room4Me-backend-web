package com.room4me.dtos.property;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;

public class ImageDTO {
  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;

  @NotBlank(message = "File name required")
  private String fileLink;

  @JsonProperty(access = Access.READ_ONLY)
  private Date createdAt;

  @JsonProperty(access = Access.READ_ONLY)
  private Date updatedAt;

  public ImageDTO() {
    super();
  }

  public ImageDTO(
    UUID id, String fileLink,
    Date createdAt, Date updatedAt
  ) {
    this.id = id;
    this.fileLink = fileLink;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFileLink() {
    return fileLink;
  }

  public void setFileLink(String fileLink) {
    this.fileLink = fileLink;
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
