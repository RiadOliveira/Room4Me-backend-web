package com.room4me.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.room4me.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
    Optional<User> findById(UUID id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.contact c WHERE u.id = ?1 ")
    User findByIdWithContact(UUID id);
}
