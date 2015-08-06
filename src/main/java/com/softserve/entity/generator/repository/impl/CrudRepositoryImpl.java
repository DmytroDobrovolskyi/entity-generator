package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Generic data access object, implementation of CRUD operation. ust only be wired and used in business logic layer classes.
 *
 * <b>Note</b>: keep in main that to make this object work properly {@literal objectClassToken} must be set, so invoke
 * {@literal setObjectClassToken} with proper argument before using this object.
 *
 * @param <T> object type
 */
@Repository
public class CrudRepositoryImpl<T extends DatabaseObject> implements CrudRepository<T>
{
    @Autowired
    protected EntityManager entityManager;

    private Class<T> objectClassToken;

    protected CrudRepositoryImpl() { }

    protected void resetEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public void setObjectClassToken(Class<T> objectClassToken)
    {
        this.objectClassToken = objectClassToken;
    }

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

    @Override
    public List<T> findAll()
    {
        return entityManager
                .createQuery("FROM " + objectClassToken.getSimpleName(), objectClassToken)
                .getResultList();
    }

    @Override
    public T findById(String id)
    {
        return entityManager.find(objectClassToken, id);
    }
}
