package com.room4me.dtos.user;

import java.util.UUID;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactDTO {
    private static final String PHONE_NUMBER_REGEX = "^\\+\\d{2}\\(\\d{2}\\)\\d{4}-\\d{4}$";

    @JsonProperty(access = Access.READ_ONLY)
    private UUID id;

    @Email(message = "Invalid e-mail format")
    @NotBlank(message = "E-mail required")
    private String email;

    @NotBlank(message = "Call number required")
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "Invalid call number format")
    private String callNumber;

    @NotBlank(message = "Whatsapp number required")
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "Invalid whatsapp number format")
    private String whatsappNumber;

    @URL(message = "Invalid URL format")
    private String instagram;

    public ContactDTO() {
        super();
    }

    public ContactDTO(
        UUID id, String email, String callNumber,
        String whatsappNumber, String instagram
    ) {
        this.id = id;
        this.email = email;
        this.callNumber = callNumber;
        this.whatsappNumber = whatsappNumber;
        this.instagram = instagram;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
