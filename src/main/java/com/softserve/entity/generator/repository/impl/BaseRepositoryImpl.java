package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.repository.BaseRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BaseRepositoryImpl<T> implements BaseRepository<T>
{
    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseRepositoryImpl(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected BaseRepositoryImpl() {}

    @Override
    public void save(T entity)
    {
        entityManager.persist(entity);
    }

    @Override
    public void delete(T entity)
    {
        entityManager.remove(entity);
    }

    @Override
    public T merge(T entity)
    {
        return entityManager.merge(entity);
    }

    @Override
    public T findById(String id)
    {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> findAll()
    {
        return entityManager
                .createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                .getResultList();
    }
}
