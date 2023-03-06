package com.room4me.repositories;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.room4me.entities.Image;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    Set<Image> findByPropertyId(UUID id);
}
