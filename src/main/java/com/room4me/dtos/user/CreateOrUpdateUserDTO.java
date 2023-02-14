package com.room4me.dtos.user;

import com.room4me.enumerators.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateOrUpdateUserDTO {
    @NotEmpty(message = "User name required")
    @NotNull(message = "User name required")
    private String name;

    @NotEmpty(message = "E-mail required")
    @NotNull(message = "E-mail required")
    @Email(message = "Invalid e-mail format")
    private String email;

    @NotEmpty(message = "Phone number required")
    @NotNull(message = "Phone number required")
    private String phoneNumber;

    @NotEmpty(message = "Password required")
    @NotNull(message = "Password required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String avatar;

    @NotEmpty(message = "Gender required")
    @NotNull(message = "Gender required")
    private Gender gender;

    public CreateOrUpdateUserDTO() {
        super();
    }

    public CreateOrUpdateUserDTO(
        String name, String email,
        String phoneNumber, String avatar,
        String password, Gender gender
    ) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.password = password;
        this.gender = gender;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
