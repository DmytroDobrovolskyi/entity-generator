package com.softserve.entity.generator.service.applier.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.applier.Applier;
import com.softserve.entity.generator.service.applier.util.ProcedureGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.softserve.entity.generator.util.LoggerUtil.logger;

@Service
public class EntityApplier implements Applier<Entity>
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void apply(Entity entity)
    {
        String createProcedureQueryString = ProcedureGenerator.generateProcedure(entity);

        logger(this).info(createProcedureQueryString);
        entityManager.createNativeQuery(createProcedureQueryString).executeUpdate();
    }
}
