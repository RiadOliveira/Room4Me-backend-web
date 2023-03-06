package com.room4me.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.room4me.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
    Optional<Property> findById(UUID id);

    @Query(
        "SELECT p FROM Property p LEFT JOIN FETCH p.address ad " +
        "LEFT JOIN FETCH p.aspects as LEFT JOIN FETCH p.owner o " +
        "LEFT JOIN FETCH p.images i WHERE p.id = ?1 "
    )
    Property findByIdWithRelatedEntities(UUID id);
}
