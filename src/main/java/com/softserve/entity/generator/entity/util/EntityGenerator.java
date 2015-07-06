package com.softserve.entity.generator.entity.util;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;

import java.util.*;

public class EntityGenerator
{
    public static Entity generateEntity()
    {
        Entity entity = new Entity("EntityId", "New table");
        entity.setTableName("NEW_TABLE");

        Field firstField = new Field();
        firstField.setName("first");
        firstField.setColumnName("First_Column");
        firstField.setType("int");

        Field secondField = new Field();
        secondField.setName("Second");
        secondField.setColumnName("Second_Column");
        secondField.setType("int");

        Field thirdField = new Field();
        thirdField.setName("Third");
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
