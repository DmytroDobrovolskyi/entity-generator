package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.repository.BaseSearchRepository;
import com.softserve.entity.generator.service.BatchService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BatchServiceImpl<T extends DatabaseObject> implements BatchService<T>
{
    private BaseSearchRepository<T> baseSearchRepository;

    @SuppressWarnings("unchecked")
    public BatchServiceImpl(Class<T> objectClassToken)
    {
        baseSearchRepository = new AnnotationConfigApplicationContext().getBean(BaseSearchRepository.class, objectClassToken);
    }

    protected BatchServiceImpl() {}

    @Override
    @Transactional
    public void batchMerge(List<T> objects)
    {
        for (T object : objects)
        {
            baseSearchRepository.merge(object);
        }
    }

    @Override
    @Transactional
    public void batchDelete(List<String> objectIdList)
    {
        for (String id : objectIdList)
        {
            baseSearchRepository.delete(
                    baseSearchRepository.findById(id)
            );
        }
    }
}
