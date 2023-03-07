package com.room4me.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.room4me.entities.FavoriteProperty;

public interface FavoritePropertyRepository extends JpaRepository<FavoriteProperty, UUID> {
    boolean existsByUserIdAndPropertyId(UUID userId, UUID propertyId);
    FavoriteProperty findByUserIdAndPropertyId(UUID userId, UUID propertyId);

    @Query("SELECT fv FROM FavoriteProperty fv LEFT JOIN FETCH fv.user u WHERE fv.id = ?1 ")
    FavoriteProperty findByIdWithUser(UUID id);

    @Query(
        "SELECT fv FROM FavoriteProperty fv LEFT JOIN FETCH fv.property p " + 
        "WHERE fv.user.id = ?1 "
    )
    List<FavoriteProperty> findByUserIdWithProperty(UUID id);
}