package com.softserve.entity.generator.util;

import javassist.NotFoundException;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil
{
    private static final Logger logger = Logger.getLogger(ReflectionUtil.class);

    /**
     * This method creates parametrized type from specific type token. For instance it serves the parsing purpose
     * for Gson library that required additional information about generic type in runtime.
     *
     * @param genericType generic type for parameterized type
     * @param rawType     raw type for parameterized type
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

    public static <T> T invokeGetter(Object objectToGetFrom, Class<T> returnTypeClass, String getterName) throws NotFoundException
    {
        for (Method method : objectToGetFrom.getClass().getMethods())
        {
            if (method.getName().equals(getterName))
            {
                try
                {
                    return castSafe(returnTypeClass, method.invoke(objectToGetFrom));
                }
                catch (IllegalAccessException ex)
                {
                    logger.error("Could not determine method: " + method.getName());
                    throw new AssertionError(ex);
                }
                catch (InvocationTargetException ex)
                {
                    logger.error("Could not determine method: " + method.getName());
                    throw new AssertionError(ex);
                }
            }
        }
        throw new NotFoundException("Could not invoke requested method");
    }

    public static void invokeSetter(Object objectToSetTo, String setterName, Object... args)
    {
        for (Method method : objectToSetTo.getClass().getMethods())
        {
            if (method.getName().equals(setterName))
            {
                try
                {
                     method.invoke(objectToSetTo, args);
                }
                catch (IllegalAccessException ex)
                {
                    logger.error("Could not determine method: " + method.getName());
                    throw new AssertionError(ex);
                }
                catch (InvocationTargetException ex)
                {
                    logger.error("Could not determine method: " + method.getName());
                    throw new AssertionError(ex);
                }
            }
        }
    }

    public static <T> T castSafe(Class<T> classToCast, Object objectToCast)
    {
        return classToCast.cast(objectToCast);
    }
}
