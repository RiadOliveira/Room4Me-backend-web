package com.room4me.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.room4me.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
