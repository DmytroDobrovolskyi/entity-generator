package com.softserve.entity.generator.app.util;

import com.softserve.entity.generator.salesforce.SalesforceAuthenticator;
import javassist.NotFoundException;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class Authenticator
{
    private static final Logger logger = Logger.getLogger(Authenticator.class);

    private static SalesforceAuthenticator salesforceAuthenticator;

    public static SalesforceAuthenticator login(String[] credentials)
    {
        String username = null;
        String password = null;
        String token = null;

        Options options = new Options();

        if (credentials.length != 0)
        {
            CommandLineParser parser = new BasicParser();

            CommandLine commandLine = null;

            options.addOption("h", "help", false, "Print help for this application");
            options.addOption("u", "username", true, "Salesforce username");
            options.addOption("p", "password", true, "Password to your organization");
            options.addOption("t", "token", true, "Security token");

            try
            {
                commandLine = parser.parse(options, credentials);

                if (commandLine.hasOption('h'))
                {
                    help(options);
                }

                username = commandLine.getOptionValue('u');
                password = commandLine.getOptionValue('p');
                token = commandLine.getOptionValue('t');

                if (username == null || password == null || token == null)
                {
                    help(options);
                    System.exit(0);
                }

                CredentialsUtil.saveUserData(username, password, token);
            }
            catch (ParseException ex)
            {
                logger.error("Failed to parse command line properties", ex);
                help(options);
                System.exit(1);
            }
        }
        else
        {
            try
            {
                Scanner credentialsScanner = CredentialsUtil.loadUserData();

                username = credentialsScanner.next();
                password = credentialsScanner.next();
                token = credentialsScanner.next();

            }
            catch (NotFoundException ex)
            {
                logger.error("User has not logged in yet");
                help(options);
                System.exit(0);
            }
            catch (IOException ex)
            {
                logger.error("User has not logged in yet");
                help(options);
                System.exit(0);
            }
        }

        return new SalesforceAuthenticator(
                username,
                password,
                token
        );
    }

    private static void help(Options options)
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Authenticator", options);
    }
}
