package com.room4me.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room4me.dtos.property.PropertyDTO;
import com.room4me.entities.Property;
import com.room4me.repositories.PropertyRepository;

import jakarta.el.PropertyNotFoundException;

@Service
public class PropertyServices {
  @Autowired
  private PropertyRepository propertyRepository;

  @Autowired
  private ModelMapper mapper;

  public PropertyDTO createPropertyDTO(PropertyDTO propertyToCreate) {
    Property property = mapper.map(propertyToCreate, Property.class);
    propertyRepository.save(property);
    return propertyToCreate;
  }

  public PropertyDTO update(UUID propertyId, PropertyDTO propertyToUpdate) {
    Optional<Property> findedPropertyOptional = propertyRepository.findById(propertyId);

    if (!findedPropertyOptional.isPresent()) {
      throw new PropertyNotFoundException("Property not found");
    }

    Property findedProperty = findedPropertyOptional.get();

    mapper.map(propertyToUpdate, findedProperty);
    propertyRepository.save(findedProperty);

    return propertyToUpdate;
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
