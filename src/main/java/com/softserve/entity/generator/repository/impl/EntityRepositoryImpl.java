package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EntityRepositoryImpl extends BaseRepositoryImpl<Entity> implements EntityRepository
{
    public EntityRepositoryImpl()
    {
        super(Entity.class);
    }
}
