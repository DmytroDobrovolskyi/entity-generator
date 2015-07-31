package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.repository.CrudRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
abstract class BaseServiceImpl<T extends DatabaseObject>
{
    private static final Logger logger = Logger.getLogger(BaseServiceImpl.class);

    @Autowired
    @Qualifier(value = "crudRepositoryImpl")
    private CrudRepository<T> crudRepository;

    private Class<T> objectClass;

    protected BaseServiceImpl(Class<T> objectClass)
    {
        this.objectClass = objectClass;
    }

    @PostConstruct
    private void init()
    {
        Assert.notNull(objectClass);
        Assert.notNull(crudRepository);
        crudRepository.setObjectClassToken(objectClass);
    }

    @Transactional
    public void save(T entity)
    {
        crudRepository.save(entity);
    }

    @Transactional
    public void delete(T entity)
    {
        crudRepository.delete(entity);
    }

    @Transactional
    public T merge(T entity)
    {
        return crudRepository.merge(entity);
    }

    @Transactional(readOnly = true)
    public T findById(String id)
    {
        return crudRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll()
    {
        return crudRepository.findAll();
    }
}
