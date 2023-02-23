package com.room4me.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.room4me.entities.Aspects;

public interface AspectsRepository extends JpaRepository<Aspects, UUID> {
}
