package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.repository.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class EntityRepositoryImpl extends BaseRepositoryImpl<Entity> implements EntityRepository
{
    public EntityRepositoryImpl()
    {
        super(Entity.class);
    }

    public Set<Field> resolveDeletedFields(Entity entity) //TODO
    {
        Set<Field> resolvedFields = new HashSet<Field>();
        for (Field field : entity.getFields())
        {
            if (field.getState().getIsDeleted())
            {
                entityManager.remove(
                        entityManager.merge(field)
                );
            }
            else
            {
                resolvedFields.add(field);
            }
        }
        return resolvedFields;
    }
}
