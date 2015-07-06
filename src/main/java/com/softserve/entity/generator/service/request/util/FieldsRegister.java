package com.softserve.entity.generator.service.request.util;

import com.softserve.entity.generator.entity.Entity;

import java.lang.reflect.Field;
import java.util.*;

import static java.util.Arrays.*;

public class FieldsRegister
{
    private static final List<Class<?>> ENTITIES = Collections.unmodifiableList(
            Arrays.<Class<?>>asList(Entity.class, com.softserve.entity.generator.entity.Field.class)
    );

    private static Map<Class<?>, String> customFieldsMap;
    private static Map<Class<?>, String> relationsMap;

    public static Map<Class<?>, String> getCustomFieldsMap()
    {
        processRegistration();
        return customFieldsMap;
    }

    public static Map<Class<?>, String> getRelationsMap()
    {
        processRegistration();
        return relationsMap;
    }

    private static void processRegistration()
    {
        if (customFieldsMap == null)
        {
            customFieldsMap = new HashMap<Class<?>, String>();

            for (Class<?> entityClass : ENTITIES)
            {
                customFieldsMap.put(
                        entityClass,
                        FiledFormatter.toSalesforceStyle(
                                filterFields(entityClass.getDeclaredFields(), true), "__c"
                        )
                );
            }
        }

        if (relationsMap == null)
        {
            relationsMap = new HashMap<Class<?>, String>();

            for (Class<?> entityClass : ENTITIES)
            {
                relationsMap.put(
                        entityClass,
                        FiledFormatter.toSalesforceStyle(
                                filterFields(entityClass.getDeclaredFields(), false), "__r"
                        )
                );
            }
        }
    }

    private static List<Field> filterFields(Field[] fields, boolean toCustom)
    {
        List<Field> filteredFields = new ArrayList<Field>();
        for (Field field : fields)
        {
            if (!field.getName().equals("name"))
            {
                boolean isRelation = Collection.class.isAssignableFrom(field.getType());

                if (toCustom && !isRelation)
                {
                    filteredFields.add(field);
                }
                else if (!toCustom && isRelation)
                {
                    filteredFields.add(field);
                }
            }
        }
        return filteredFields;
    }

    public static void main(String[] args)
    {
        System.out.println(getCustomFieldsMap().get(Entity.class));
        System.out.println(getRelationsMap().get(Entity.class));
    }
}

