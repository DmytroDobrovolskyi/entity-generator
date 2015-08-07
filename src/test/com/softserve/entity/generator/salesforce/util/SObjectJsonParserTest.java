package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.entity.production.Entity;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class SObjectJsonParserTest
{
    private static final String PATH_TO_RESOURCES = System.getProperty("user.dir") + "/src/test/resources/";
    private File file;
    private Scanner scanner;

    @Test
    public void testParseSObjectJson()
    {
        assertEquals
                (
                        this.getFormattedJsonFromFile().toString().trim(),
                        SObjectJsonParser.toJavaStyleJsonArray(getUnformattedJsonFromFile().toString(), Entity.class).trim()
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

    private StringBuilder getFormattedJsonFromFile()
    {
        file = new File(PATH_TO_RESOURCES + "FormattedJSON");
        StringBuilder stringBuilder = new StringBuilder();

        try
        {

            scanner = new Scanner(file);

            while (scanner.hasNextLine())
            {
                stringBuilder
                        .append(scanner.nextLine())
                        .append("\n");
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
