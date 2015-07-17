package com.softserve.entity.generator.service.salesforce.util;

import java.lang.reflect.Field;
import java.util.*;

public class ColumnsRegisterProcessor
{
    public static Map<Class<?>, String> processRegistration(List<Class<?>> registerMetadata, List<String> exclusions)
    {
        Map<Class<?>, String> register = new HashMap<Class<?>, String>();

        for (Class<?> entityClass : registerMetadata)
        {
            register.put(
                    entityClass,
                    ColumnFormatter.toSalesforceStyle(
                            filterFields(entityClass.getDeclaredFields(), registerMetadata, exclusions), "__c"
                    )
            );
        }
        return register;
    }

    private static List<Field> filterFields(Field[] fields, List<Class<?>> registerMetadata, List<String> exclusions)
    {
        List<Field> filteredFields = new ArrayList<Field>();
        for (Field field : fields)
        {
            if (!field.getName().equals("name"))
            {
                Class<?> filedType = field.getType();
                boolean isRelation = Collection.class.isAssignableFrom(filedType) || registerMetadata.contains(filedType);

                if (!isRelation && !exclusions.contains(field.getName()))
                {
                    filteredFields.add(field);
                }
            }
        }
        return filteredFields;
    }
}
