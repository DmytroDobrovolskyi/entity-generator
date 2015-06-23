package com.softserve.entity.generator;

import com.softserve.entity.generator.config.JpaConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.service.applier.Applier;
import com.softserve.entity.generator.service.EntityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class);

    @Autowired
    private Applier applier;

    @Autowired
    private EntityService entityService;

    public static void main(String[] args)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        Main main = context.getBean(Main.class);
        main.testConfig();
    }

    public void testConfig()
    {
        entityService.merge(new Entity("11", "Any"));
    }

    public void testApplier()
    {
        applier.createTable(generateEntity());
    }

    private Entity generateEntity()
    {
        Entity entity = new Entity("XXX", "XXX");
        entity.setTableName("NEWTABLE");

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
