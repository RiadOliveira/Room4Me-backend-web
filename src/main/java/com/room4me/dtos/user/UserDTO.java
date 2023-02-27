package com.room4me.dtos.user;

import java.util.Date;
import java.util.UUID;

import com.room4me.enumerators.Gender;

public class UserDTO {
    private UUID id;

    private String name;
    private String email;
    private String avatar;
    private Gender gender;

    private Date createdAt;
    private Date updatedAt;

    public UserDTO() {
        super();
    }

    public UserDTO(
        UUID id, String name, String email,
        String avatar, Gender gender,
        Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.gender = gender;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
