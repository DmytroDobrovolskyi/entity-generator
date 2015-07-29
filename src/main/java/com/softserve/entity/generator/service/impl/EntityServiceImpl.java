package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.repository.EntityRepository;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.webservice.util.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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
    public void processBatchOperation(Map<Entity, OperationType> entitiesToSync)
    {
        for (Map.Entry<Entity, OperationType> entry : entitiesToSync.entrySet())
        {
            Entity entityToSync = entry.getKey();
            for (Field field : entityToSync.getFields())
            {
                field.setEntity(entityToSync);
            }
            Entity managedEntity = entityRepository.merge(entityToSync);
            if (entry.getValue().equals(OperationType.DELETE_OPERATION))
            {
                entityRepository.delete(managedEntity);
            }
        }
    }
}
