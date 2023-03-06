package com.room4me.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.room4me.dtos.property.PropertyDTO;
import com.room4me.entities.Property;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.PropertyRepository;
import com.room4me.repositories.UserRepository;
import com.room4me.utils.ObjectPropsInjector;
import com.room4me.utils.RepositoryUtils;

import jakarta.el.PropertyNotFoundException;

@Service
public class PropertyServices {
  @Autowired
  private PropertyRepository propertyRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper mapper;

  public PropertyDTO createProperty(
    UUID ownerId, PropertyDTO propertyToCreate
  ) {
    User findedUser = RepositoryUtils.findEntityByIdOrThrowException(
      ownerId, userRepository, User.class
    );

    Property property = mapper.map(propertyToCreate, Property.class);
    property.setOwner(findedUser);
    property.getAddress().setProperty(property);
    property.getAspects().setProperty(property);

    property = propertyRepository.save(property);
    return mapper.map(property, PropertyDTO.class);
  }

  public PropertyDTO update(
    UUID ownerId, UUID propertyId, PropertyDTO propertyToUpdate
  ) {
    Property findedProperty = propertyRepository.findByIdWithRelatedEntities(
      propertyId
    );
    if (findedProperty == null) {
      throw new ServerException(
        "Property not found", HttpStatus.NOT_FOUND
      );
    }

    PropertyDTO mappedEntity = mapper.map(findedProperty, PropertyDTO.class);
    ObjectPropsInjector.injectFromAnotherObject(
      propertyToUpdate, mappedEntity
    );
    ObjectPropsInjector.injectFromAnotherObject(
      propertyToUpdate.getAddress(), mappedEntity.getAddress()
    );
    ObjectPropsInjector.injectFromAnotherObject(
      propertyToUpdate.getAspects(), mappedEntity.getAspects()
    );

    Property updatedPropertyEntity = mapper.map(
      propertyToUpdate, Property.class
    );
    updatedPropertyEntity.setOwner(findedProperty.getOwner());
    updatedPropertyEntity.getAddress().setProperty(updatedPropertyEntity);
    updatedPropertyEntity.getAspects().setProperty(updatedPropertyEntity);

    updatedPropertyEntity = propertyRepository.save(findedProperty);
    updatedPropertyEntity.setImages(findedProperty.getImages());

    return mapper.map(updatedPropertyEntity, PropertyDTO.class);
  }

  public void deleteProperty(UUID propertyId) {
    propertyRepository.deleteById(propertyId);
  }

  public List<PropertyDTO> findAll() {
    List<Property> findedProperties = propertyRepository.findAll();
    return findedProperties.stream().map(property -> mapper.map(findedProperties, PropertyDTO.class))
        .collect(Collectors.toList());
  }

  public PropertyDTO findById(UUID id) {
    Optional<Property> findedProperty = propertyRepository.findById(id);

    if (!findedProperty.isPresent()) {
      throw new PropertyNotFoundException("Property not found");
    }

    return mapper.map(findedProperty, PropertyDTO.class);
  }
}
