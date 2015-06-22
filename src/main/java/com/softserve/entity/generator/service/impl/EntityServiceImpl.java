package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.repository.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class EntityServiceImpl extends BaseRepositoryImpl<Entity> implements EntityService
{
    public EntityServiceImpl()
    {
        super(Entity.class);
    }
}
