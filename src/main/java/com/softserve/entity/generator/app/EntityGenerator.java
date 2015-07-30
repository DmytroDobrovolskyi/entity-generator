package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.LoginUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.salesforce.SalesforceCredentials;
import com.softserve.entity.generator.salesforce.ProcedureExecutor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EntityGenerator
{
    private static final Logger logger = Logger.getLogger(EntityGenerator.class);

    @Autowired
    private ProcedureExecutor procedureExecutor;

    public static void main(String[] args)
    {
        SalesforceCredentials salesforceCredentials = LoginUtil.parseCredentials(args);

        EntityGenerator entityGenerator = new AnnotationConfigApplicationContext(AppConfig.class).getBean(EntityGenerator.class);

        entityGenerator.generateEntities(salesforceCredentials);
    }

    public void generateEntities(SalesforceCredentials salesforceCredentials)
    {
        procedureExecutor.generateAndExecute(salesforceCredentials);
    }
}
