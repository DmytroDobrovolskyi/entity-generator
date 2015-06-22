package com.softserve.entity.generator.procedure.applier;

import com.softserve.entity.generator.entity.Entity;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.StringWriter;

@Service
public class Applier
{
    private static final Logger logger = Logger.getLogger(Applier.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createTable(Entity entity)
    {
        VelocityEngine velocityEngine = getVelocityEngine();
        velocityEngine.init();
        Template templateCreate = velocityEngine.getTemplate("velocity.template/ProcedureCreator.vm");
        VelocityContext context = new VelocityContext();

        context.put("procedureName", "myProcedure");
        context.put("entity", entity);

        StringWriter writer = new StringWriter();
        templateCreate.merge(context, writer);
        String sqlQuery = writer.toString();
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