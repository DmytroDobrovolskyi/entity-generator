package com.softserve.entity.generator.app.util;

import javassist.NotFoundException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Scanner;

public class CredentialsUtil
{
    private static final Logger logger = Logger.getLogger(Authenticator.class);
    private static final String FILE_NAME = ".credentials";

    public static void saveUserData(String username, String password, String token)
    {
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(username);
            out.writeObject(password);
            out.writeObject(token);

            out.close();
    }
        catch (IOException ex)
        {
            logger.error("Failed to save user credentials", ex);
            throw new AssertionError(ex);
        }
    }

    public static Scanner loadUserData() throws NotFoundException, IOException
    {
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            return new Scanner(
                    in.readObject().toString() + " " +
                    in.readObject().toString() + " " +
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
