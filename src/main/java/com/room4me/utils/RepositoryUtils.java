package com.room4me.utils;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import com.room4me.errors.ServerException;

public class RepositoryUtils {
    public static <T, R extends JpaRepository<T, UUID>>T findEntityByIdOrThrowException(
        UUID entityId, R repository, Class<T> entityClass
    ) {
        String entityName = entityClass.getSimpleName();

        T findedEntity = repository.findById(entityId).orElse(null);
        if(findedEntity == null) {
            throw new ServerException(
                entityName + " not found", HttpStatus.NOT_FOUND
            );
        }

        return findedEntity;
    }
}
