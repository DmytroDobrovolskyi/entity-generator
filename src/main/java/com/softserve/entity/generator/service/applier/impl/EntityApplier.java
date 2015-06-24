package com.softserve.entity.generator.service.applier.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.applier.Applier;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.StringWriter;

@Service
public class EntityApplier implements Applier<Entity>
{
    private static final Logger logger = Logger.getLogger(EntityApplier.class);
    private static final String PROCEDURE_NAME = "myProcedure";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void apply(Entity entity)
    {
        createProcedure(entity);
        entityManager.createNativeQuery("EXEC " + PROCEDURE_NAME).executeUpdate();
    }

    private void createProcedure(Entity entity)
    {
        VelocityEngine velocityEngine = getVelocityEngine();
        velocityEngine.init();
        Template templateCreate = velocityEngine.getTemplate("velocity.template/ProcedureCreator.vm");
        VelocityContext context = new VelocityContext();

        context.put("procedureName", PROCEDURE_NAME);
        context.put("entity", entity);

        StringWriter writer = new StringWriter();
        templateCreate.merge(context, writer);

        String sqlQuery = writer.toString();
        Query query = entityManager.createNativeQuery("SELECT cast(name as varchar) FROM sys.objects " +
                "WHERE name ='" + PROCEDURE_NAME + "'");
        if (query.getResultList().size() != 0)
        {
            entityManager.createNativeQuery("DROP PROCEDURE" + query.getResultList()).executeUpdate();
        }

        logger.info(sqlQuery);
        entityManager.createNativeQuery(sqlQuery).executeUpdate();
    }

    private VelocityEngine getVelocityEngine()
    {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return velocityEngine;
    }
}
