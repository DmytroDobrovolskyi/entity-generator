package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.Authenticator;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.salesforce.Credentials;
import com.softserve.entity.generator.salesforce.SObjectRequester;
import com.softserve.entity.generator.service.EntityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntitySaver
{
    private static final Logger logger = Logger.getLogger(EntitySaver.class);

    @Autowired
    private EntityService entityService;

    public static void main(String[] args)
    {
        Credentials credentials = Authenticator.login(args);

        SObjectRequester<Field> SObjectRequester = new SObjectRequester<Field>(credentials, Field.class);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        EntitySaver entitySaver = applicationContext.getBean(EntitySaver.class);

        SObjectRequester.getAll();

//        entitySaver.saveEntities();
    }

    public void saveEntities(List<Entity> receivedEntities)
    {
        entityService.resolveDeleted(receivedEntities);
        for (Entity receivedEntity : receivedEntities)
        {
            entityService.merge(receivedEntity);
        }
    }
}
