package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.LoginUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.EntityService;
import org.apache.log4j.Logger;

public class EntityGenerator
{
    private static final Logger logger = Logger.getLogger(EntityGenerator.class);
    private static final String RESET_IS_PROCESSING_NEEDED = "EntityUtil.resetIsProcessingNeeded();";

    public static void main(String[] args)
    {
        EntityService entityService = AppContextCache.getContext(AppConfig.class).getBean(EntityService.class);
        entityService.applyData();

        WebServiceUtil.getInstance(LoginUtil.parseCredentials(args)).executeApex(RESET_IS_PROCESSING_NEEDED);
    }
}
