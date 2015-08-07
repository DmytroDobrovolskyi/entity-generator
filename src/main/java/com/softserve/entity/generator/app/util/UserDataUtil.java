package com.softserve.entity.generator.app.util;

import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

public class UserDataUtil
{
    private static final Logger logger = Logger.getLogger(UserDataUtil.class);

    public static SalesforceCredentials parseCredentials(String[] credentials)
    {
        Options options = new Options();

        options.addOption("h", "help", false, "Print help for this application");
        options.addOption("u", "username", true, "Salesforce username");
        options.addOption("p", "password", true, "Password to your organization");
        options.addOption("t", "token", true, "Security token");
        options.addOption("orgId", "organizationId", true, "Organization id");

        try
        {
            CommandLineParser parser = new BasicParser();
            CommandLine commandLine = parser.parse(options, credentials);

            if (commandLine.hasOption('h'))
            {
                help(options);
            }

            String username = commandLine.getOptionValue('u');
            String password = commandLine.getOptionValue('p');
            String token = commandLine.getOptionValue('t');
            String organizationId = commandLine.getOptionValue("orgId");

            if (username == null || password == null || token == null || organizationId == null)
            {
                help(options);
                System.exit(1);
            }

            return new SalesforceCredentials(username, password, token, organizationId);
        }
        catch (ParseException ex)
        {
            logger.error("Failed to parse command line parameters");
            help(options);
            System.exit(1);
        }
        return null; //won't be executed
    }

    public static String parseUsername(String[] credentials)
    {
        Options options = new Options();
        options.addOption("u", "username", true, "Salesforce username");
        try
        {
            CommandLineParser parser = new BasicParser();
            CommandLine commandLine = parser.parse(options, credentials);

            if (commandLine.hasOption('h'))
            {
                help(options);
            }

            String username = commandLine.getOptionValue('u');

            if (username == null)
            {
                help(options);
                System.exit(1);
            }

            return username;
        }
        catch (ParseException ex)
        {
            logger.error("Failed to parse command line parameters");
            help(options);
            throw new AssertionError(ex);
        }
    }

    public static void checkArgs(String[] args)
    {
        if (args.length == 0)
        {
            logger.info("Enter username please");
            System.exit(1);
        }
    }

    public static void checkCredentials(SalesforceCredentials userCredentials)
    {
        if (userCredentials == null)
        {
            logger.error("User not found");
            System.exit(1);
        }
    }

    private static void help(Options options)
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Add user", options);
    }
}
