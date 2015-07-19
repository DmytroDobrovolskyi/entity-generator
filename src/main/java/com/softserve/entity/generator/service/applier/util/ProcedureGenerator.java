package com.softserve.entity.generator.service.applier.util;

import com.softserve.entity.generator.config.DatabaseInitializationConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.StringWriter;
import java.util.List;

public class ProcedureGenerator
{
    private static final Logger logger = Logger.getLogger(ProcedureGenerator.class);

    public static final String PROCEDURE_NAME = "generateTable";

    public static String generateProcedure(Entity entity)
    {
        VelocityEngine velocityEngine = getVelocityEngine();
        velocityEngine.init();
        Template templateCreate = velocityEngine.getTemplate("velocity.template/stored-procedure-template.vm");
        VelocityContext context = new VelocityContext();

        context.put("schema", DatabaseInitializationConfig.GENERATED_TABLES_SCHEMA);
        context.put("procedureName", PROCEDURE_NAME);
        context.put("entity", entity);
        context.put("entityClass", Entity.class);
        context.put("fieldClass", Field.class);

        StringWriter writer = new StringWriter();
        templateCreate.merge(context, writer);

        return writer.toString();
    }

    public static String generateProcedure(List<Entity> entities)
    {
        VelocityEngine velocityEngine = getVelocityEngine();
        velocityEngine.init();
        Template templateCreate = velocityEngine.getTemplate("velocity.template/stored-procedure-template.vm");
        VelocityContext context = new VelocityContext();

        context.put("schema", DatabaseInitializationConfig.GENERATED_TABLES_SCHEMA);
        context.put("procedureName", PROCEDURE_NAME);
        context.put("entities", entities);
        context.put("entityClass", Entity.class);
        context.put("fieldClass", Field.class);

        StringWriter writer = new StringWriter();
        templateCreate.merge(context, writer);

        logger.info(writer.toString());
        return writer.toString();
    }

    private static VelocityEngine getVelocityEngine()
    {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.apache.velocity.runtime.log.NullLogChute");
        return velocityEngine;
    }
}
