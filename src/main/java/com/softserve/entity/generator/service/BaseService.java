package com.softserve.entity.generator.service;

import org.springframework.stereotype.Service;

@Service
public interface BaseService<T>
{
    void save(T entity);

    void delete(T entity);

    T merge(T entity);

    T findById(String id);
}
