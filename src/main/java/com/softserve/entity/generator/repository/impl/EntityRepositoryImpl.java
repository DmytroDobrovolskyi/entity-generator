package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EntityRepositoryImpl extends CrudRepositoryImpl<Entity> implements EntityRepository
{
    private EntityRepositoryImpl()
    {
        super.setObjectClassToken(Entity.class);
    }
}
