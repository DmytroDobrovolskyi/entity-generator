package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class BaseRepositoryImpl<T> implements BaseRepository<T>
{
    private Class<T> entityClass;

    public BaseRepositoryImpl(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected BaseRepositoryImpl() {}

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(T entity)
    {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void delete(T entity)
    {
        entityManager.remove(entity);
    }

    @Override
    @Transactional
    public T merge(T entity)
    {
        return entityManager.merge(entity);
    }

    @Override
    public T findById(String id)
    {
        return entityManager.find(entityClass, id);
    }
}
