package com.room4me.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginDTO {
    @NotBlank(message = "E-mail required")
    @Email(message = "Invalid e-mail format")
    private String email;

    @NotNull(message = "Password required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public LoginDTO() {
        super();
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
}
