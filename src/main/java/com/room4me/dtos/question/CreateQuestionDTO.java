package com.room4me.dtos.question;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateQuestionDTO {
    @NotNull(message = "Property Id required")
    private UUID propertyId;

    @NotBlank(message = "Content required")
    private String content;

    public CreateQuestionDTO() {
        super();
    }

    public CreateQuestionDTO(UUID propertyId, String content) {
        this.propertyId = propertyId;
        this.content = content;
    }

    public UUID getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(UUID propertyId) {
        this.propertyId = propertyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
