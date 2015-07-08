package com.softserve.entity.generator.service.applier.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.applier.Applier;
import com.softserve.entity.generator.service.applier.util.ProcedureGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class EntityApplier implements Applier<Entity>
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean apply(Entity entity)
    {
        if (entity.getFields().size() != 0 || entity.getState().getIsDeleted())
        {
            String createProcedureQueryString = ProcedureGenerator.generateProcedure(entity);

            entityManager.createNativeQuery(createProcedureQueryString).executeUpdate();
            return true;
        }
        return false;
    }
}
