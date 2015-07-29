package com.softserve.entity.generator.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil
{

    /**
     * This method creates parametrized type from specific type token. For instance it serves the parsing purpose
     * for Gson library that requireq additional information about generic type in runtime.
     *
     * @param genericType generic type for parameterized type
     * @param rawType raw type for parameterized type
     * @return parameterized type with specific {@literal genericType}
     */
    public static ParameterizedType getParametrizedType(final Class<?> genericType, final Class<?> rawType)
    {
        return new ParameterizedType()
        {
            @Override
            public Type[] getActualTypeArguments()
            {
                return new Type[]{genericType};
            }

            @Override
            public Type getRawType()
            {
                return rawType;
            }

            @Override
            public Type getOwnerType()
            {
                return null;
            }
        };
    }

    public static <T> T castSafe(Class<T> classToCast, Object objectToCast)
    {
       return classToCast.cast(objectToCast);
    }
}
