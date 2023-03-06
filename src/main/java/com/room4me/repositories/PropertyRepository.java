package com.room4me.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.room4me.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
    public final String findWithRelatedEntitiesQuery =
        "SELECT p FROM Property p LEFT JOIN FETCH p.address ad " +
        "LEFT JOIN FETCH p.aspects as LEFT JOIN FETCH p.owner o ";

    Optional<Property> findById(UUID id);

    @Query(findWithRelatedEntitiesQuery + "WHERE p.id = ?1 ")
    Property findByIdWithRelatedEntities(UUID id);

    @Query(
        findWithRelatedEntitiesQuery +
        "LEFT JOIN FETCH o.contact WHERE p.id = ?1 "
    )
    Property findByIdWithRelatedEntitiesAndOwnerContact(UUID id);

    @Query(
        "SELECT p FROM Property p LEFT JOIN FETCH p.owner o " +
        "WHERE p.id = ?1 "
    )
    Property findByIdWithOwnerData(UUID id);

    @Query(
        "SELECT p FROM Property p LEFT JOIN FETCH p.address ad " +
        "LEFT JOIN FETCH p.aspects as "
    )
    List<Property> findAllWithRelatedEntitiesExceptUser();
}
