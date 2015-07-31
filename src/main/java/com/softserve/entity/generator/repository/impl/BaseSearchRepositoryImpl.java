package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.repository.BaseSearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BaseSearchRepositoryImpl<T extends DatabaseObject> extends BaseRepositoryImpl<T> implements BaseSearchRepository<T>
{
    private Class<T> objectClassToken;

    public BaseSearchRepositoryImpl(Class<T> objectClassToken)
    {

        this.objectClassToken = objectClassToken;
    }

    protected BaseSearchRepositoryImpl() { }

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
