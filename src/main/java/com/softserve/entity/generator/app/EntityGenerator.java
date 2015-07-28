package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.Authenticator;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.salesforce.Credentials;
import com.softserve.entity.generator.salesforce.ProcedureExecutor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
        Credentials credentials = Authenticator.login(args);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        EntityGenerator entityGenerator = applicationContext.getBean(EntityGenerator.class);

        entityGenerator.generateEntities(credentials);
    }

    public void generateEntities(Credentials credentials)
    {
        procedureExecutor.generateAndExecute(credentials);
    }
}
