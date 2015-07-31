package com.softserve.entity.generator.service;

import org.springframework.stereotype.Service;

@Service
public interface BaseService<T>
{
    void save(T object);

    void delete(T object);

    T merge(T object);
}
