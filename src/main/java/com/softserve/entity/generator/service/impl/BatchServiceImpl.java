package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.repository.CrudRepository;
import com.softserve.entity.generator.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BatchServiceImpl<T extends DatabaseObject> implements BatchService<T>
{
    @Autowired
    @Qualifier(value = "crudRepositoryImpl")
    private CrudRepository<T> crudRepository;

    private BatchServiceImpl() { }

    @Override
    @Transactional
    public void batchMerge(List<T> objects)
    {
        for (T object : objects)
        {
            crudRepository.merge(object);
        }
    }

    @Override
    @Transactional
    public void batchDelete(List<String> objectIdList, Class<T> objectClass)
    {
        crudRepository.setObjectClassToken(objectClass);

        for (String id : objectIdList)
        {
            crudRepository.delete(
                    crudRepository.findById(id)
            );
        }
    }
}
