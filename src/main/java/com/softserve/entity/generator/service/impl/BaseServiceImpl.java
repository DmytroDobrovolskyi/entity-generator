package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.repository.BaseRepository;
import com.softserve.entity.generator.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseServiceImpl<T> implements BaseService<T>
{
    @Autowired
    @Qualifier(value = "baseRepositoryImpl")
    private BaseRepository<T> baseRepository;

    @Override
    @Transactional
    public void save(T entity)
    {
        baseRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(T entity)
    {
        baseRepository.delete(entity);
    }

    @Override
    @Transactional
    public T merge(T entity)
    {
        return baseRepository.merge(entity);
    }

    @Override
    public T findById(String id)
    {
        return baseRepository.findById(id);
    }
}