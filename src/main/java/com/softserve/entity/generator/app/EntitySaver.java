package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.LoginUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.production.Entity;
import com.softserve.entity.generator.entity.production.Field;
import com.softserve.entity.generator.salesforce.FetchType;
import com.softserve.entity.generator.salesforce.SObjectProcessor;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.BatchService;
import com.softserve.entity.generator.service.EntityService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class EntitySaver
{
    private static final Logger logger = Logger.getLogger(EntitySaver.class);

    public static void main(String[] args)
    {
        WebServiceUtil webServiceUtil = WebServiceUtil.getInstance(LoginUtil.parseCredentials(args));
        SObjectProcessor<Entity> sObjectProcessor = SObjectProcessor.getInstance(webServiceUtil.getSessionId(), Entity.class);

        ApplicationContext context = AppContextCache.getContext(AppConfig.class);

        @SuppressWarnings("unchecked")
        BatchService<Entity> batchService = context.getBean(BatchService.class);
        EntityService entityService = context.getBean(EntityService.class);

        List<Entity> entitiesToSync = sObjectProcessor.getAll(FetchType.EAGER);

        doParentToChildAssignment(entitiesToSync);
        batchService.batchMerge(entitiesToSync);
    }

    private static void doParentToChildAssignment(List<Entity> entitiesToSync)
    {
        for (Entity entityToSync : entitiesToSync)
        {
            for (Field filed : entityToSync.getFields())
            {
                filed.setEntity(entityToSync);
            }
        }
    }
}
