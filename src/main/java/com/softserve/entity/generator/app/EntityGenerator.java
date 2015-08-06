package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.UserDataUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.UserDataService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class EntityGenerator
{
    private static final Logger logger = Logger.getLogger(EntityGenerator.class);
    private static final String RESET_IS_PROCESSING_NEEDED = "EntityUtil.resetIsProcessingNeeded();";

    public static void main(String[] args)
    {
        UserDataUtil.checkUsername(args[0]);

        ApplicationContext context = AppContextCache.getContext(AppConfig.class);

        EntityService entityService = context.getBean(EntityService.class);
        entityService.applyData();

        SalesforceCredentials credentials = context.getBean(UserDataService.class).findUser(args[0]);
        WebServiceUtil.getInstance(credentials).executeApex(RESET_IS_PROCESSING_NEEDED);
    }
}
