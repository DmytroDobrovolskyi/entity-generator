package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.salesforce.util.ColumnsRegisterProcessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ColumnsRegister
{
    private static final List<Class<?>> ENTITIES = Collections.unmodifiableList(
            Arrays.<Class<?>>asList(Entity.class, com.softserve.entity.generator.entity.Field.class)
    );

    private static final List<String> EXCLUSIONS = Collections.unmodifiableList(
            Arrays.<String>asList("name")
    );

    private static Map<Class<?>, SObjectMetadata> register;

    public static SObjectMetadata getSObjectMetadata(Class<?> sObjectClass)
    {
        if (register == null)
        {
            register = ColumnsRegisterProcessor.processRegistration(ENTITIES, EXCLUSIONS);
        }
        return register.get(sObjectClass);
    }

    public static String getFullName(String className) throws ClassNotFoundException
    {
        for (Class<?> aClass : ENTITIES)
        {
            if (aClass.getSimpleName().equals(className))
            {
                return aClass.getName();
            }
        }
        throw new ClassNotFoundException();
    }
}
