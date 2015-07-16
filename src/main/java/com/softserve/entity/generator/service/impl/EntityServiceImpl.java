package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.repository.EntityRepository;
import com.softserve.entity.generator.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
}
