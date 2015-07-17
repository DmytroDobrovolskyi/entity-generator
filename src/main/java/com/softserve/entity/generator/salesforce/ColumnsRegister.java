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
            Arrays.<String>asList("")
    );

    private static Map<Class<?>, String> customFieldsMap;

    public static Map<Class<?>, String> getCustomFieldsMap()
    {
        if (customFieldsMap == null)
        {
            customFieldsMap = ColumnsRegisterProcessor.processRegistration(ENTITIES, EXCLUSIONS);
        }
        return customFieldsMap;
    }
}
