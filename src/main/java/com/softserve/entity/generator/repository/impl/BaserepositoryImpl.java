package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.repository.BaseRepositore;

public class BaserepositoryImpl<T> implements BaseRepositore<T>{

    @Override
    public void save(T entity) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void upsert(T entity) {

    }

    @Override
    public T findById(String id) {
        return null;
    }
}
