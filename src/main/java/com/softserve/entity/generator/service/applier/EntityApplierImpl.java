package com.softserve.entity.generator.service.applier;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.applier.util.ProcedureGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Primary
public class EntityApplierImpl implements EntityApplier
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void apply(Entity entity)
    {
        if (entity.getFields().size() != 0)
        {
            String createProcedureQueryString = ProcedureGenerator.generateProcedure(entity);

            entityManager.createNativeQuery(createProcedureQueryString).executeUpdate();
        }
    }

    @Override
    @Transactional
    public void applyAll(List<Entity> entities)
    {
        String createProcedureQueryString = ProcedureGenerator.generateProcedure(entities);

        entityManager.createNativeQuery(createProcedureQueryString).executeUpdate();
    }
}
