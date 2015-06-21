package com.softserve.entity.generator.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BaseRepository<T>
{
    void save(T entity);

    void delete(T entity);

    T merge(T entity);

    T findById(String id);
}
