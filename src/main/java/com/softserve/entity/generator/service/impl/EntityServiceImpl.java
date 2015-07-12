package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.EntityRepository;
import com.softserve.entity.generator.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityServiceImpl extends BaseServiceImpl<Entity> implements EntityService
{
    @Autowired
    private EntityRepository entityRepository;

    @Override
    @Transactional
    public void mergeAndResolveDeletedFields(Entity entity)
    {
        entity.setFields(
                entityRepository.resolveDeletedFields(entity)
        );
        entityRepository.merge(entity);
    }
}
