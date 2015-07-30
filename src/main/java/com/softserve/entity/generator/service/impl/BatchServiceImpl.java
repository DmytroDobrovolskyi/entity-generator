package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.repository.BaseRepository;
import com.softserve.entity.generator.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BatchServiceImpl<T> implements BatchService<T>
{
    @Autowired
    @Qualifier(value = "baseRepositoryImpl")
    private BaseRepository<T> baseRepository;

    @Override
    @Transactional
    public void batchMerge(List<T> objects)
    {
        for (T object : objects)
        {
            baseRepository.merge(object);
        }
    }

    @Override
    @Transactional
    public void batchDelete(List<String> objectIdList)
    {
        for (String id : objectIdList)
        {
            baseRepository.delete(
                    baseRepository.findById(id)
            );
        }
    }
}
