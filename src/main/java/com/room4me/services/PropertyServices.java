package com.room4me.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.room4me.dtos.property.PropertyDTO;
import com.room4me.dtos.user.ContactDTO;
import com.room4me.dtos.user.UserDTO;
import com.room4me.dtos.user.UserWithContactDTO;
import com.room4me.entities.Contact;
import com.room4me.entities.Property;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.PropertyRepository;
import com.room4me.repositories.UserRepository;
import com.room4me.utils.ObjectPropsInjector;
import com.room4me.utils.UserUtils;

@Service
public class PropertyServices {
  @Autowired
  private PropertyRepository propertyRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ImageServices imageServices;

  @Autowired
  private ModelMapper mapper;

  public PropertyDTO create(
    UUID userId, PropertyDTO propertyToCreate
  ) {
    User findedUser = userRepository.findByIdWithContact(userId);
    if(findedUser == null) {
      throw new ServerException(
        "User not found", HttpStatus.NOT_FOUND
      );
    }
    if(findedUser.getContact() == null) {
      throw new ServerException(
        "User needs to have contact information to create a property",
        HttpStatus.FORBIDDEN
      );
    }

    Property property = mapper.map(propertyToCreate, Property.class);
    property.setOwner(findedUser);
    property.getAddress().setProperty(property);
    property.getAspects().setProperty(property);

    property = propertyRepository.save(property);
    return mapper.map(property, PropertyDTO.class);
  }

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

  public List<PropertyDTO> findAll() {
    List<Property> findedProperties = propertyRepository
      .findAllWithRelatedEntitiesExceptUser();

    return findedProperties.stream().map(
      property -> {
        property.setImages(
          imageServices.findByPropertyIdWithParsedLink(property.getId())
        );
        return mapper.map(property, PropertyDTO.class);
      }
    ).collect(Collectors.toList());
  }

  public List<PropertyDTO> findAllByUserId(UUID userId) {
    List<Property> findedProperties = propertyRepository
      .findAllByUserIdWithRelatedEntitiesExceptUser(userId);
    
    return findedProperties.stream().map(
      property -> {
        property.setImages(
          imageServices.findByPropertyIdWithParsedLink(property.getId())
        );
        return mapper.map(property, PropertyDTO.class);
      }
    ).collect(Collectors.toList());
  }

  private UserWithContactDTO getOwnerWithContactFromPropertyEntity(
    Property propertyEntity
  ) {
    UserWithContactDTO ownerWithContact = new UserWithContactDTO();

    User owner = propertyEntity.getOwner();
    ownerWithContact.setUser(mapper.map(owner, UserDTO.class));

    Contact ownerContact = owner.getContact();
    ownerWithContact.setContact(
      mapper.map(ownerContact, ContactDTO.class)
    );

    return ownerWithContact;
  }

  public PropertyDTO findById(UUID propertyId) {
    Property findedProperty = propertyRepository
      .findByIdWithRelatedEntitiesAndOwnerContact(propertyId);
    if (findedProperty == null) {
      throw new ServerException(
        "Property not found", HttpStatus.NOT_FOUND
      );
    }

    findedProperty.setImages(
      imageServices.findByPropertyIdWithParsedLink(propertyId)
    );
    PropertyDTO parsedProperty = mapper.map(findedProperty, PropertyDTO.class);
    
    UserWithContactDTO ownerWithContact = getOwnerWithContactFromPropertyEntity(
      findedProperty
    );
    UserUtils.setCompleteUserAvatarLink(ownerWithContact.getUser());
    parsedProperty.setOwnerWithContact(ownerWithContact);

    return parsedProperty;
  }

  public PropertyDTO update(
    UUID userId, UUID propertyId, PropertyDTO propertyToUpdate
  ) {
    Property findedProperty = propertyRepository.findByIdWithRelatedEntities(
      propertyId
    );
    validatePropertyAndOwner(userId, findedProperty);
    findedProperty.setImages(
      imageServices.findByPropertyIdWithParsedLink(propertyId)
    );

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

  public void delete(UUID userId, UUID propertyId) {
    Property findedProperty = propertyRepository.findByIdWithOwner(
      propertyId
    );

    validatePropertyAndOwner(userId, findedProperty);
    propertyRepository.deleteById(propertyId);
  }
}
