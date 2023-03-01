package com.room4me.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.room4me.dtos.user.CreateOrUpdateUserDTO;
import com.room4me.dtos.user.LoginDTO;
import com.room4me.dtos.user.UserDTO;
import com.room4me.services.UserServices;
import com.room4me.utils.JwtUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping("")
    public ResponseEntity<?> createUser(
        @Valid @RequestBody CreateOrUpdateUserDTO userToCreate
    ) {
        UserDTO response = userServices.createUser(userToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sessions")
    public ResponseEntity<?> createSessions(
        @Valid @RequestBody LoginDTO loginData
    ) {
        UserDTO response = userServices.createSessions(loginData);

        String token = JwtUtils.generateToken(response.getId().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set(
            "Authorization",
            JwtUtils.TOKEN_PREFIX + " " + token
        );
        headers.set(
            "Access-Control-Expose-Headers",
            "Authorization"
        );

        return ResponseEntity.status(
            HttpStatus.OK
        ).headers(headers).body(response);
    }
}
