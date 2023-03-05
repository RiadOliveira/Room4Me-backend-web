package com.room4me.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.room4me.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
    Optional<Property> findById(UUID id);
}
