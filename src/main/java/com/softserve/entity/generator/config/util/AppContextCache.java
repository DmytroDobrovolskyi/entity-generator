package com.softserve.entity.generator.config.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppContextCache
{
    public static ApplicationContext context;

    public static ApplicationContext getContext(Class<?> configClass)
    {
        if (context == null)
        {
            context = new AnnotationConfigApplicationContext(configClass);
        }
        return context;
    }
}
