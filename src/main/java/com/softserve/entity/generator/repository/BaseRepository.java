package com.softserve.entity.generator.repository;

public interface BaseRepository<T> {
    void save(T entity);
    void delete(T entity);
    void deleteById(String id);
    void upsert(T entity);
    T findById(String id);

}
