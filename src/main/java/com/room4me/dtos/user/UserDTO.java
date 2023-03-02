package com.room4me.dtos.user;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.room4me.enumerators.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @NotBlank(message = "User name required")
    private String name;

    @NotBlank(message = "E-mail required")
    @Email(message = "Invalid e-mail format")
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotNull(message = "Password required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String avatar;

    @NotNull(message = "Gender required")
    private Gender gender;

    @JsonProperty(access = Access.READ_ONLY)
    private UUID id;

    @JsonProperty(access = Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = Access.READ_ONLY)
    private Date updatedAt;

    public UserDTO() {
        super();
    }

    public UserDTO(
        String name, String email, String password,
        String avatar, Gender gender, UUID id,
        Date createdAt, Date updatedAt
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.gender = gender;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
