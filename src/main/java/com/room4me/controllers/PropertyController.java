package com.room4me.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.room4me.dtos.property.PropertyDTO;
import com.room4me.services.PropertyServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    private PropertyServices propertyServices;

    @GetMapping("")
    public ResponseEntity<?> findAllProperties() {
        List<PropertyDTO> response = propertyServices.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<?> findPropertyById(
        @PathVariable UUID propertyId
    ) {
        PropertyDTO response = propertyServices.findById(propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createProperty(
        @RequestAttribute UUID userId,
        @Valid @RequestBody PropertyDTO propertyToCreate
    ) {
        PropertyDTO response = propertyServices.create(
            userId, propertyToCreate
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<?> updateProperty(
        @RequestAttribute UUID userId,
        @PathVariable UUID propertyId,
        @Valid @RequestBody PropertyDTO propertyToUpdate
    ) {
        PropertyDTO response = propertyServices.update(
            userId, propertyId, propertyToUpdate
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<?> deleteProperty(
        @RequestAttribute UUID userId,
        @PathVariable UUID propertyId
    ) {
        propertyServices.delete(userId, propertyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
