package com.softserve.entity.generator;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.service.request.EntityRequester;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App
{
    private static final Logger logger = Logger.getLogger(App.class);



    public static void main(String[] args)
    {
        if (args[0] != null)
        {
            args = args[0].split(" ");
        }

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
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            App app = context.getBean(App.class);
            app.showAllEntities(commandLine.getOptionValue('u'), commandLine.getOptionValue('p'),
                    commandLine.getOptionValue('t'));
        }
        catch (ParseException ex)
        {
            logger.info("Failed to parse command line properties", ex);
        }
    }

    public void showAllEntities(String username, String password, String secToken)
    {
        logger.info(new EntityRequester(username, password, secToken).getAllEntities());
    }

    private static void help(Options options)
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("App", options);
    }
}
