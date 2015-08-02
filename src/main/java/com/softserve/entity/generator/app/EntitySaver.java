package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.LoginUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.salesforce.SObjectProcessor;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.BatchService;
import org.apache.log4j.Logger;

public class EntitySaver
{
    private static final Logger logger = Logger.getLogger(EntitySaver.class);

    public static void main(String[] args)
    {
        WebServiceUtil webServiceUtil = WebServiceUtil.getInstance(LoginUtil.parseCredentials(args));
        SObjectProcessor<Entity> sObjectProcessor = SObjectProcessor.getInstance(webServiceUtil.getSessionId(), Entity.class);

        @SuppressWarnings("unchecked")
        BatchService<Entity> batchService = AppContextCache.getContext(AppConfig.class).getBean(BatchService.class);

        batchService.batchMerge(sObjectProcessor.getAll());
    }
}
