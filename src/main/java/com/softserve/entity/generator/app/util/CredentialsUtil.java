package com.softserve.entity.generator.app.util;

import com.softserve.entity.generator.salesforce.SalesforceCredentials;
import javassist.NotFoundException;
import org.apache.log4j.Logger;

import java.io.*;

public class CredentialsUtil
{
    private static final Logger logger = Logger.getLogger(LoginUtil.class);
    private static final String FILE_NAME = ".credentials";

    public static void saveCredentials(SalesforceCredentials salesforceCredentials)
    {
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(salesforceCredentials.getUsername());
            out.writeObject(salesforceCredentials.getPassword());
            out.writeObject(salesforceCredentials.getToken());

            out.close();
    }
        catch (IOException ex)
        {
            logger.error("Failed to save user credentials", ex);
            throw new AssertionError(ex);
        }
    }

    public static SalesforceCredentials loadCredentials() throws NotFoundException
    {
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            return new SalesforceCredentials(
                    in.readObject().toString(),
                    in.readObject().toString(),
                    in.readObject().toString()
            );
        }
        catch (IOException ex)
        {
            throw new NotFoundException("User has not logged in yet");
        }
        catch (ClassNotFoundException ex)
        {
            throw new NotFoundException("User has not logged in yet");
        }
    }
}
