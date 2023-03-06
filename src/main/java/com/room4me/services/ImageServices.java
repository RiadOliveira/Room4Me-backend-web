package com.room4me.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.room4me.dtos.property.ImageDTO;
import com.room4me.entities.Image;
import com.room4me.entities.Property;
import com.room4me.errors.ServerException;
import com.room4me.repositories.ImageRepository;
import com.room4me.repositories.PropertyRepository;
import com.room4me.utils.ImageUtils;

@Service
public class ImageServices {
    private static final long PROPERTY_IMAGES_LIMIT = 24;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private FileAPIServices fileAPIServices;

    @Autowired
    private ModelMapper mapper;

    private void validatePropertyAndOwner(
        UUID userId, Property property
    ) {
        if (property == null) {
            throw new ServerException(
                "Property not found", HttpStatus.NOT_FOUND
            );
        }

        UUID ownerId = property.getOwner().getId();
        if(!ownerId.equals(userId)) {
            throw new ServerException(
                "Requested user does not have permission to manipulate this property",
                HttpStatus.UNAUTHORIZED
            );
        }
    }

    private void validateImagesQuantity(
        UUID propertyId, int requestedImagesQuantity
    ) {
        long imagesQuantity = imageRepository.countByPropertyId(propertyId);

        if(imagesQuantity == PROPERTY_IMAGES_LIMIT) {
            throw new ServerException(
                "This property has reached the limit of images",
                HttpStatus.FORBIDDEN
            );
        }
        if(imagesQuantity + requestedImagesQuantity > PROPERTY_IMAGES_LIMIT) {
            throw new ServerException(
                "Requested images quantity exceeded property's limit",
                HttpStatus.FORBIDDEN
            );
        }
    }

    public List<ImageDTO> createToProperty(
        UUID userId, UUID propertyId,
        List<MultipartFile> images
    ) {
        Property findedProperty = propertyRepository.findByIdWithOwner(
            propertyId
        );
        validatePropertyAndOwner(userId, findedProperty);
        validateImagesQuantity(propertyId, images.size());

        List<Image> entityList = images.stream().map(
            image -> {
                String url = fileAPIServices.sendFile(image);

                Image entity = new Image();
                entity.setFileLink(FileAPIServices.getLinkUniquePart(url));
                entity.setProperty(findedProperty);

                return entity;
            }
        ).collect(Collectors.toList());
        entityList = imageRepository.saveAll(entityList);

        return entityList.stream().map(
            image -> {
                ImageUtils.setCompleteImageLink(image);
                return mapper.map(image, ImageDTO.class);
            }
        ).collect(Collectors.toList());
    }

    public void delete(UUID userId, UUID imageId) {
        Image findedImage = imageRepository.findByIdWithPropertyAndOwner(
            imageId
        );
        if(findedImage == null) {
            throw new ServerException(
                "Image not found", HttpStatus.NOT_FOUND
            );
        }

        validatePropertyAndOwner(userId, findedImage.getProperty());
        imageRepository.deleteById(imageId);
    }

    public Set<Image> findByPropertyIdWithParsedLink(UUID propertyId) {
        return imageRepository.findByPropertyId(propertyId).stream().map(
            image -> {
                ImageUtils.setCompleteImageLink(image);
                return image;
            }
        ).collect(Collectors.toSet());
    }
}
