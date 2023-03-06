package com.room4me.dtos.property;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;

public class ImageDTO {
  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;

  @NotBlank(message = "File name required")
  private String fileLink;

  public ImageDTO() {
    super();
  }

  public ImageDTO(
    UUID id, String fileLink
  ) {
    this.id = id;
    this.fileLink = fileLink;
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
}
