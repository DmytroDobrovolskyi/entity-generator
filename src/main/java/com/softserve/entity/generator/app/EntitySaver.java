package com.softserve.entity.generator.app;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EntitySaver
{
    private static final Logger logger = Logger.getLogger(EntitySaver.class);

    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(applicationContext.getBean(BaseService.class));
    }
}
