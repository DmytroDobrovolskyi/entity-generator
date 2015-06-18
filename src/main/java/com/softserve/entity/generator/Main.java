package com.softserve.entity.generator;

import com.softserve.entity.generator.config.EntityManagerConfigurator;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.persistence.EntityManager;
import java.io.StringWriter;
import java.util.*;

public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class);
    private static final EntityManager entityManager = EntityManagerConfigurator.getEntityManager();

    public static void main(String[] args)
    {
            createTable(generateEntity());
    }

    private static void createTable(Entity entity)
    {
        VelocityEngine velocityEngine = getVelocityEngine();
        velocityEngine.init();

        Template templateCreate = velocityEngine.getTemplate("Entity.vm");
        VelocityContext context = new VelocityContext();
        Map<String, String> columns = new HashMap<String, String>();

        context.put("procedureName", entity.getId());
        context.put("tableName", entity.getTableName());

        for(Field field:entity.getFields())
        {
            columns.put(field.getColumnName(),field.getType());
        }
        context.put("columns", columns);

        StringWriter writer = new StringWriter();
        templateCreate.merge(context, writer);

        String sqlQuery = writer.toString();
        System.out.println(writer.toString());

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sqlQuery).executeUpdate();
        entityManager.getTransaction().commit();
        EntityManagerConfigurator.closeSession();
    }

    private static VelocityEngine getVelocityEngine()
    {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return velocityEngine;
    }
    private static Entity generateEntity()
    {
        Entity entity = new Entity();
        entity.setId("ProcN");
        entity.setTableName("Table_Name");

        Field firstField = new Field();
        firstField.setColumnName("First_Column");
        firstField.setType("int");

        Field secondField = new Field();
        secondField.setColumnName("Second_Column");
        secondField.setType("varchar");

        Set<Field>fields = new HashSet<Field>();
        fields.add(firstField);
        fields.add(secondField);

        entity.setFields(fields);

        return entity;
    }
}
