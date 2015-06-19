package com.softserve.entity.generator.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T>
{
    void save(T entity);

    void delete(T entity);

    T merge(T entity);

    T findById(String id);
}
