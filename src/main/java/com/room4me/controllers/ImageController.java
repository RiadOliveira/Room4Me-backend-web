package com.room4me.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.room4me.dtos.property.ImageDTO;
import com.room4me.services.ImageServices;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageServices imageServices;

    @PostMapping(
        path = "/createToProperty/{propertyId}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> createImageToProperty(
        @RequestAttribute UUID userId,
        @PathVariable UUID propertyId,
        @RequestParam("image") List<MultipartFile> images
    ) {
        List<ImageDTO> response = imageServices.createToProperty(
            userId, propertyId, images
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImageFromProperty(
        @RequestAttribute UUID userId, @PathVariable UUID imageId
    ) {
        imageServices.delete(userId, imageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
