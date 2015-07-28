package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.repository.EntityRepository;
import com.softserve.entity.generator.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EntityServiceImpl extends BaseServiceImpl<Entity> implements EntityService
{
    @Autowired
    private EntityRepository entityRepository;

    @Override
    @Transactional
    public void resolveDeleted(List<Entity> entities)
    {
        for (Entity managedEntity : entityRepository.findAll())
        {
            if (!entities.contains(managedEntity))
            {
                entityRepository.delete(managedEntity);
            }
        }
    }

    @Override
    @Transactional
    public void batchMerge(List<Entity> entities)
    {
        for (Entity entity : entities)
        {
            for (Field field : entity.getFields())
            {
                field.setEntity(entity);
            }
            entityRepository.merge(entity);
        }
    }
}
