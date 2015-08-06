package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.repository.CrudRepository;
import com.softserve.entity.generator.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Primary
abstract class BaseServiceImpl<T extends DatabaseObject> implements BaseService<T>
{
    private static final Logger logger = Logger.getLogger(BaseServiceImpl.class);

    @Autowired
    @Qualifier(value = "crudRepositoryImpl")
    private CrudRepository<T> crudRepository;

    protected BaseServiceImpl() { }

    @Override
    public void setObjectClassToken(Class<T> objectClassToken)
    {
        Assert.notNull(crudRepository);
        crudRepository.setObjectClassToken(objectClassToken);
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
