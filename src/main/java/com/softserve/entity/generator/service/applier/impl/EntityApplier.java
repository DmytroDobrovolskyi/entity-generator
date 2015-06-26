package com.softserve.entity.generator.service.applier.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.applier.Applier;
import com.softserve.entity.generator.service.applier.util.ProcedureGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.softserve.entity.generator.service.applier.util.ProcedureGenerator.PROCEDURE_NAME;
import static com.softserve.entity.generator.util.LoggerUtil.logger;

@Service
public class EntityApplier implements Applier<Entity>
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    //TODO: SINGLE QUERY, OLES!!!
    public void apply(Entity entity)
    {
        String createProcedureQueryString = ProcedureGenerator.generateProcedure(entity);
        Query selectProcedureQuery = entityManager.createNativeQuery
                (
                                "SELECT cast(name as varchar) " +
                                "FROM sys.objects " +
                                "WHERE name ='" + PROCEDURE_NAME + "'"
                );

        List resultList = selectProcedureQuery.getResultList();

        if (resultList.size() != 0)
        {
            entityManager.createNativeQuery("DROP PROCEDURE" + resultList).executeUpdate();
        }

        logger(this).info(createProcedureQueryString);
        entityManager.createNativeQuery(createProcedureQueryString).executeUpdate();

        entityManager.createNativeQuery("EXEC " + PROCEDURE_NAME).executeUpdate();
    }
}
