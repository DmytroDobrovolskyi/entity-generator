package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.Entity;
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
    public void saveAndResolveDeleted(List<Entity> entities)
    {
        for (Entity entity : entityRepository.findAll())
        {
            if (!entities.contains(entity))
            {
                entityRepository.delete(entity);
            }
        }

        for (Entity entity : entities)
        {
            entityRepository.merge(entity);
        }
    }
}
