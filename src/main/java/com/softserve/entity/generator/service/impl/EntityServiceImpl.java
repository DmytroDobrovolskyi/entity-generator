package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.production.Entity;
import com.softserve.entity.generator.repository.EntityRepository;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.EntityApplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class EntityServiceImpl extends BaseServiceImpl<Entity> implements EntityService
{
    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private EntityApplier entityApplier;

    private EntityServiceImpl() { }

    @PostConstruct
    private void init()
    {
        super.setObjectClassToken(Entity.class);
    }

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
    public void applyData()
    {
        List<Entity> entitiesToProcess = entityRepository.findAll();

        entityApplier.applyAll(entitiesToProcess);

        for (Entity entity : entitiesToProcess)
        {
            entity.setIsProcessingNeeded(false);
            entityRepository.merge(entity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Entity getByFieldId(String fieldId)
    {
        return entityRepository.getByFieldId(fieldId);
    }
}
