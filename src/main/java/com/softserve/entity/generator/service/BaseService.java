package com.softserve.entity.generator.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<T>
{
    void save(T entity);

    void delete(T entity);

    T merge(T entity);

    T findById(String id);

    List<T> findAll();
}
