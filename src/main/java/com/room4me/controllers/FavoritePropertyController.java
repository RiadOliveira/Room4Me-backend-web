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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.room4me.dtos.FavoritePropertyDTO;
import com.room4me.services.FavoritePropertyServices;

@RestController
@RequestMapping("/favoriteProperty")
public class FavoritePropertyController {
    @Autowired
    private FavoritePropertyServices favoritePropertyServices;

    @GetMapping("/findAllByUserId/{userId}")
    public ResponseEntity<?> findAllFavoritePropertiesByUserId(
        @PathVariable UUID userId
    ) {
        List<FavoritePropertyDTO> response = favoritePropertyServices.findAllByUser(
            userId
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<?> createFavoriteProperty(
        @RequestAttribute UUID userId, @PathVariable UUID propertyId
    ) {
        FavoritePropertyDTO response = favoritePropertyServices.create(userId, propertyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<?> deleteFavoriteProperty(
        @RequestAttribute UUID userId, @PathVariable UUID propertyId
    ) {
        favoritePropertyServices.delete(userId, propertyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
