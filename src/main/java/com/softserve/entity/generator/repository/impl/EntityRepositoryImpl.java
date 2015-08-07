package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.entity.production.Entity;
import com.softserve.entity.generator.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EntityRepositoryImpl extends CrudRepositoryImpl<Entity> implements EntityRepository
{
    private EntityRepositoryImpl()
    {
        super.setObjectClassToken(Entity.class);
    }

    @Override
    public Entity getByFieldId(String fieldId)
    {
        return entityManager.createQuery(
                "FROM Entity AS e " +
                "WHERE e.entityId " +
                "IN " +
                "(" +
                    "SELECT entity " +
                    "FROM Field AS f " +
                    "WHERE f.fieldId = '"+ fieldId +"'" +
                ")",
                Entity.class)
                    .getSingleResult();
    }
}
