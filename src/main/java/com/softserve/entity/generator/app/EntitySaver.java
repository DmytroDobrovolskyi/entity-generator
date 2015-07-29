package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.LoginUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.salesforce.SObjectProcessor;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.webservice.util.OperationType;
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
        WebServiceUtil webServiceUtil = WebServiceUtil.getInstance(LoginUtil.parseCredentials(args));
        SObjectProcessor<Entity> SObjectProcessor = new SObjectProcessor<Entity>(webServiceUtil.getSessionId(), Entity.class);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        EntitySaver entitySaver = applicationContext.getBean(EntitySaver.class);

        entitySaver.saveEntities(SObjectProcessor.getAll());
    }

    public void saveEntities(List<Entity> receivedEntities)
    {
        entityService.processBatchOperation(receivedEntities, OperationType.UPDATE_OPERATION);
    }
}
