package com.softserve.entity.generator;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.EntityApplier;
import com.softserve.entity.generator.service.salesforce.ApexExecutor;
import com.softserve.entity.generator.service.salesforce.Authenticator;
import com.softserve.entity.generator.service.salesforce.EntityRequester;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class App
{
    private static final Logger logger = Logger.getLogger(App.class);
    private static final String APEX_METHOD_TO_EXECUTE = "EntityUtil.resetIsProcessingNeeded();";

    @Autowired
    private EntityService entityService;

    @Autowired
    private EntityApplier entityApplier;

    private static Authenticator authenticator;

    public static void main(String[] args)
    {
        CommandLineParser parser = new BasicParser();

        CommandLine commandLine = null;

        Options options = new Options();

        options.addOption("h", "help", false, "Print help for this application");
        options.addOption("u", "username", true, "Salesforce username");
        options.addOption("p", "password", true, "Password to your organization");
        options.addOption("t", "token", true, "Security token");

        try
        {
            commandLine = parser.parse(options, args);

            if (commandLine.hasOption('h'))
            {
                help(options);
            }

            String username = commandLine.getOptionValue('u');
            String password = commandLine.getOptionValue('p');
            String token = commandLine.getOptionValue('t');

            if (username == null || password == null || token == null)
            {
                help(options);
                System.exit(0);
            }

            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            App app = context.getBean(App.class);

            authenticator = new Authenticator(
                    username,
                    password,
                    token
            );

            EntityRequester entityRequester = new EntityRequester(authenticator);

            app.saveEntities(entityRequester.getAllEntities());

            app.executeProcedures();
        }
        catch (ParseException ex)
        {
            logger.info("Failed to parse command line properties", ex);
            help(options);
        }
    }

    public void saveEntities(List<Entity> receivedEntities)
    {
        entityService.resolveDeleted(receivedEntities);
        entityService.trackChanges(receivedEntities);
        for (Entity receivedEntity : receivedEntities)
        {
            entityService.merge(receivedEntity);
        }
    }

    public void executeProcedures()
    {
        entityApplier.applyAll(
                entityService.findAll()
        );
        ApexExecutor.executeApex(authenticator.getLoginResult(), APEX_METHOD_TO_EXECUTE);
    }

    private static void help(Options options)
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("App", options);
    }
}
