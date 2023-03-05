package com.room4me.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.room4me.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.property p WHERE q.id = ?1 ")
    Question findByIdWithProperty(UUID id);

    @Query(
        "SELECT q FROM Question q LEFT JOIN FETCH q.questioner qner " +
        "WHERE q.property.id = ?1 ORDER BY q.createdAt DESC "
    )
    List<Question> findAllByPropertyId(UUID propertyId);
}
