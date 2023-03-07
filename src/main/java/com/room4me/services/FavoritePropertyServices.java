package com.room4me.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room4me.dtos.FavoritePropertyDTO;
import com.room4me.entities.FavoriteProperty;
import com.room4me.entities.Property;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.FavoritePropertyRepository;
import com.room4me.repositories.PropertyRepository;
import com.room4me.repositories.UserRepository;
import com.room4me.utils.RepositoryUtils;

@Service
public class FavoritePropertyServices {
    @Autowired
    private FavoritePropertyRepository favoritePropertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ModelMapper mapper;

    public FavoritePropertyDTO create(
        UUID userId, UUID propertyId
    ) {
        User findedUser = RepositoryUtils.findEntityByIdOrThrowException(
            userId, userRepository, User.class
        );
        Property findedProperty = RepositoryUtils.findEntityByIdOrThrowException(
            propertyId, propertyRepository, Property.class
        );

        boolean existsByUserAndProperty = favoritePropertyRepository.existsByUserIdAndPropertyId(
            userId, propertyId
        );
        if(existsByUserAndProperty) {
            throw new ServerException(
                "Requested user has already favorited this property"
            );
        }

        FavoriteProperty favoritePropertyToCreate = new FavoriteProperty();
        favoritePropertyToCreate.setUser(findedUser);
        favoritePropertyToCreate.setProperty(findedProperty);

        favoritePropertyToCreate = favoritePropertyRepository.save(
            favoritePropertyToCreate
        );

        return mapper.map(favoritePropertyToCreate, FavoritePropertyDTO.class);
    }

    public List<FavoritePropertyDTO> findAllByUser(UUID userId) {
        List<FavoriteProperty> findedFavoriteProperties = favoritePropertyRepository
            .findByUserIdWithProperty(userId);

        return findedFavoriteProperties.stream().map(
            favoriteProperty -> mapper.map(favoriteProperty, FavoritePropertyDTO.class)
        ).collect(Collectors.toList());
    }

    public void delete(UUID userId, UUID propertyId) {
        FavoriteProperty findedFavoriteProperty = favoritePropertyRepository.findByUserIdAndPropertyId(
            userId, propertyId
        );
        if(findedFavoriteProperty == null) {
            throw new ServerException(
                "Requested property is not one of user's favorites"
            );
        }

        favoritePropertyRepository.deleteById(findedFavoriteProperty.getId());
    }
}
