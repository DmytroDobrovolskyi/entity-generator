package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.SObjectMetadata;

import java.lang.reflect.Field;
import java.util.*;

public class SObjectRegisterProcessor
{
    public static Map<Class<?>, SObjectMetadata> processRegistration(List<Class<?>> registerMetadata, List<String> exclusions)
    {
        Map<Class<?>, SObjectMetadata> register = new HashMap<Class<?>, SObjectMetadata>();

        for (Class<?> entityClass : registerMetadata)
        {
            register.put(
                    entityClass,
                    processFields(entityClass.getDeclaredFields(), registerMetadata, exclusions)
            );
        }
        return register;
    }

    private static SObjectMetadata processFields(Field[] fieldsToProcess, List<Class<?>> registerMetadata, List<String> exclusions)
    {
        List<String> nonRelationalFields = new ArrayList<String>();
        List<String> relationalFields = new ArrayList<String>();

        ObjectType objectType = ObjectType.SUBORDER_OBJECT;

        for (Field field : fieldsToProcess)
        {
            if (exclusions.contains(field.getName()))
            {
                continue;
            }
            Class<?> filedType = field.getType();

            boolean hasChildren = Collection.class.isAssignableFrom(filedType);
            if (hasChildren)
            {
                objectType = ObjectType.HIGH_ORDER_OBJECT;
            }

            boolean isRelational =  hasChildren || registerMetadata.contains(filedType);

            if (isRelational)
            {
                relationalFields.add(ParsingUtil.toSalesforceStyleField(field, "__r"));
            }
            else
            {
                nonRelationalFields.add(ParsingUtil.toSalesforceStyleField(field, "__c"));
            }
        }
        return new SObjectMetadata(nonRelationalFields, relationalFields, objectType);
    }
}
