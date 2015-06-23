package com.softserve.entity.generator.entity.util;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;

import java.util.HashSet;
import java.util.Set;

public class EntityGenerator
{
    public static Entity generateEntity()
    {
        Entity entity = new Entity("NEW_TABLE", "New table");

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
