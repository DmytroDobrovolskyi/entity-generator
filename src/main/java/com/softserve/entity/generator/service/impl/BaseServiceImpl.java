package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.repository.BaseRepository;
import com.softserve.entity.generator.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
public class BaseServiceImpl<T> implements BaseService<T>
{
    @Autowired
    private BaseRepository<T> repository;

    protected BaseServiceImpl(){}

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

    @Override
    @Transactional(readOnly = true)
    public T findById(String id)
    {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll()
    {
        return repository.findAll();
    }
}
