package com.softserve.entity.generator.service;

import java.util.List;

/**
 * Generic business logic object. Main purpose of this object is to simplify business layer object implementations.
 * Made for inheritance and should be used with a care in other circumstances: proper {@literal objectClassToken} must be set.
 *
 * @param <T> object type
 */
public interface BaseService<T>
{
    void setObjectClassToken(Class<T> objectClassToken);

    void save(T object);

    void delete(T object);

    T merge(T object);

    T findById(String id);

    List<T> findAll();
}
