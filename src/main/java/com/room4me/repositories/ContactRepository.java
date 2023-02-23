package com.room4me.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.room4me.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
