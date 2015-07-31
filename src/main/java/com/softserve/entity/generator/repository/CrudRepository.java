package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.entity.DatabaseObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Generic data access object able to perform CreateReadUpdateDelete operations.
 * Must only be wired and used in business logic layer classes.
 *
 * @param <T> object type
 */
@Repository
public interface CrudRepository<T extends DatabaseObject>
{
    void setObjectClassToken(Class<T> objectClassToken);

    void save(T object);

    void delete(T object);

    T merge(T object);

    T findById(String id);

    List<T> findAll();
}
