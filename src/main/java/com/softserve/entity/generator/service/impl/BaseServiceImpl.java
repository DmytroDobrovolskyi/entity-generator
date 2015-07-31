package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.repository.BaseRepository;
import com.softserve.entity.generator.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class BaseServiceImpl<T extends DatabaseObject> implements BaseService<T>
{
    @Autowired
    @Qualifier(value = "baseRepositoryImpl")
    private BaseRepository<T> repository;

    @Override
    @Transactional
    public void save(T entity)
    {
        repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(T entity)
    {
        repository.delete(entity);
    }

    @Override
    @Transactional
    public T merge(T entity)
    {
        return repository.merge(entity);
    }
}
