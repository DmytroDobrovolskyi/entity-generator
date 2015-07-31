package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.repository.BaseRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Primary
public class BaseRepositoryImpl<T extends DatabaseObject> implements BaseRepository<T>
{
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void save(T object)
    {
        entityManager.persist(object);
    }

    @Override
    public void delete(T object)
    {
        entityManager.remove(object);
    }

    @Override
    public T merge(T object)
    {
        return entityManager.merge(object);
    }
}
