package com.room4me.repositories;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.room4me.entities.Image;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    @Query(
        "SELECT i FROM Image i LEFT JOIN FETCH i.property p " +
        "LEFT JOIN FETCH p.owner WHERE i.id = ?1 "
    )
    Image findByIdWithPropertyAndOwner(UUID id);

    Set<Image> findByPropertyId(UUID id);
    long countByPropertyId(UUID id);
}
