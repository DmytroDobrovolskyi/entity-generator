package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.LoginUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.salesforce.SalesforceCredentials;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.EntityService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EntityGenerator
{
    private static final Logger logger = Logger.getLogger(EntityGenerator.class);
    private static final String RESET_IS_PROCESSING_NEEDED = "EntityUtil.resetIsProcessingNeeded();";

    public static void main(String[] args)
    {
        SalesforceCredentials salesforceCredentials = LoginUtil.parseCredentials(args);

        EntityService entityService = new AnnotationConfigApplicationContext(AppConfig.class).getBean(EntityService.class);

        entityService.applyData();
        WebServiceUtil.getInstance(salesforceCredentials).executeApex(RESET_IS_PROCESSING_NEEDED);
    }
}
