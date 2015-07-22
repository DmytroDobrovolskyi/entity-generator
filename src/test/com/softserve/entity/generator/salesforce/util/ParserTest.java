package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class ParserTest
{
    private static final String PATH_TO_RESOURCES = System.getProperty("user.dir") + "\\src\\test\\resources\\";
    private File file;
    private Scanner scanner;

    @Test
    public void testParseSObjectJson()
    {
        assertEquals
                (
                        this.getformattedJsonFromFile().toString().trim(),
                        Parser.parseSObjectJson(getUnformattedJsonFromFile().toString(), Entity.class, Field.class).trim()
                );
    }

    private StringBuilder getUnformattedJsonFromFile()
    {
        System.out.println(getClass().getPackage().getClass());
        file = new File(PATH_TO_RESOURCES + "UnformattedJSON");
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            scanner = new Scanner(file);

            while (scanner.hasNextLine())
            {
                stringBuilder.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return stringBuilder;
    }

    private StringBuilder getformattedJsonFromFile()
    {
        file = new File(PATH_TO_RESOURCES + "FormattedJSON");
        StringBuilder stringBuilder = new StringBuilder();

        try
        {

            scanner = new Scanner(file);

            while (scanner.hasNextLine())
            {
                stringBuilder.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return stringBuilder;
    }
}
