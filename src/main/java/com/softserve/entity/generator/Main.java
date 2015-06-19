package com.softserve.entity.generator;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.service.EntityService;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.StringWriter;
import java.util.*;

@Component
public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityService entityService;

    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext
                ("spring/ApplicationContext.xml");
        Main main = context.getBean(Main.class);
        main.test();
    }

    public void test()
    {
        Entity entity = entityService.findById("1");
        logger.info(entity.getName());
    }

    @Transactional
    public void createTable(Entity entity)
    {
        List<String> tableName = new ArrayList<String>();
        logger.info(entityManager);
        Query tableQuery = entityManager.createNativeQuery(
                "SELECT cast(name as varchar) FROM sys.tables"
        );

        for (Object table : tableQuery.getResultList())
        {
            tableName.add((String) table);
        }

        List<String> proceduresName = new ArrayList<String>();
        Query procQuery = entityManager.createNativeQuery(
                "SELECT cast(name as varchar) FROM EntityGenerator.sys.procedures"
        );

        for (Object procedure : procQuery.getResultList())
        {
            proceduresName.add((String) procedure);
        }

        VelocityEngine velocityEngine = getVelocityEngine();
        velocityEngine.init();
        Template templateCreate = velocityEngine.getTemplate("ProcedureCreator.vm");
        VelocityContext context = new VelocityContext();
        Map<String, String> columns = new TreeMap<String, String>();

        context.put("procedureName", entity.getId());
        context.put("tableName", entity.getTableName());
        context.put("columns", columns);
        context.put("procedures", proceduresName);
        context.put("containProcedure", false);
        context.put("containTable", false);
        context.put("tables", tableName);

        for (Field field : entity.getFields())
        {
            columns.put(field.getColumnName(), field.getType());
        }

        StringWriter writer = new StringWriter();
        templateCreate.merge(context, writer);

        String sqlQuery = writer.toString();
        System.out.println(writer.toString());

        entityManager.createNativeQuery(sqlQuery).executeUpdate();
        entityManager.close();
    }

    private VelocityEngine getVelocityEngine()
    {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return velocityEngine;
    }

    private Entity generateEntity()
    {
        Entity entity = new Entity();
        entity.setId("ProcedureNa");
        entity.setTableName("FIELD");

        Field firstField = new Field();
        firstField.setColumnName("First_Column");
        firstField.setType("int");

        Field secondField = new Field();
        secondField.setColumnName("Second_Column");
        secondField.setType("int");

        Field thirdField = new Field();
        thirdField.setColumnName("Third_Column");
        thirdField.setType("int");

        Set<Field> fields = new HashSet<Field>();
        fields.add(firstField);
        fields.add(secondField);
        fields.add(thirdField);

        entity.setFields(fields);

        return entity;
    }
}
