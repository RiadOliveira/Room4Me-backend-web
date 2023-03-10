package com.room4me.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createUser(
        @Valid @ModelAttribute UserDTO userToCreate,
        @RequestParam("avatar") Optional<MultipartFile> avatar
    ) {
        UserDTO response = userServices.create(userToCreate, avatar);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sessions")
    public ResponseEntity<?> createSessions(
        @Valid @RequestBody LoginDTO loginData
    ) {
        UserDTO response = userServices.createSession(loginData);

        String token = JwtUtils.generateToken(response.getId().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set(
            "Authorization",
            token
        );
        headers.set(
            "Access-Control-Expose-Headers",
            "Authorization"
        );

        return ResponseEntity.status(
            HttpStatus.OK
        ).headers(headers).body(response);
    }

    @PutMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateUser(
        @RequestAttribute UUID userId,
        @Valid @ModelAttribute UserDTO userToUpdate,
        @RequestParam("avatar") Optional<MultipartFile> avatar
    ) {
        UserDTO response = userServices.update(
            userId, userToUpdate, avatar
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(
        @RequestAttribute UUID userId
    ) {
        userServices.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteAvatar(
        @RequestAttribute UUID userId
    ) {
        userServices.deleteAvatar(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
