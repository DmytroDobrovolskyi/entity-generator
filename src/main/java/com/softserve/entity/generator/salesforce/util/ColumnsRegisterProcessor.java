package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.SObjectMetadata;

import java.lang.reflect.Field;
import java.util.*;

public class ColumnsRegisterProcessor
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

        for (Field field : fieldsToProcess)
        {
            if (exclusions.contains(field.getName()))
            {
                continue;
            }
            Class<?> filedType = field.getType();
            boolean isRelational = Collection.class.isAssignableFrom(filedType) || registerMetadata.contains(filedType);

            if (isRelational)
            {
                relationalFields.add(ParsingUtil.toSalesforceStyle(field, "__r"));
            }
            else
            {
                nonRelationalFields.add(ParsingUtil.toSalesforceStyle(field, "__c"));
            }
        }
        return new SObjectMetadata(nonRelationalFields, relationalFields);
    }
}
