package com.room4me.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.room4me.dtos.user.ContactDTO;
import com.room4me.services.ContactServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactServices contactServices;

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<?> findByUserId(
        @PathVariable UUID userId
    ) {
        ContactDTO response = contactServices.findByUserId(
            userId
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("")
    public ResponseEntity<?> updateUserContact(
        @RequestAttribute UUID userId,
        @Valid @RequestBody ContactDTO contactData
    ) {
        ContactDTO response = contactServices.updateUserContact(
            userId, contactData
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }    
}
