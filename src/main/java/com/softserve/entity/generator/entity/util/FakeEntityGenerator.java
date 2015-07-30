package com.softserve.entity.generator.entity.util;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;

import java.util.*;

public class FakeEntityGenerator
{
    public static Entity generateEntity()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");

        Field firstField = new Field("first", "First_Column", "int");
        Field secondField = new Field("Second", "Second_Column", "int");
        Field thirdField = new Field("Third", "Third_Column", "int");

        Set<Field> fields = new HashSet<Field>();
        fields.add(firstField);
        fields.add(secondField);
        fields.add(thirdField);
        entity.setFields(fields);

        return entity;
    }
}
