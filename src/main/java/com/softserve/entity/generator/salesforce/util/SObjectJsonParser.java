package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.SObjectMetadata;
import com.softserve.entity.generator.salesforce.SObjectRegister;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SObjectJsonParser
{
    private static final Logger logger = Logger.getLogger(SObjectJsonParser.class);

    private static final String NAME_REGEX = "(\"Name\" : (.*)?(,?))";
    private static final String FIELD_NAME_REGEX = "\"(.*?)\"";
    private static final String TOTAL_SIZE_REGEX = "\"totalSize\" : (\\d+)";
    private static final String RELATIONS_TOTAL_SIZE_REGEX = "__r\" : \\{\\s+\"totalSize\" : (\\d+)";

    private static int OBJECTS_PER_LOOP = 1;

    public static String toJavaStyleJsonArray(String sObjectJson, Class<?> sObjectClass)
    {
        return doParse(sObjectJson, sObjectClass);
    }

    public static String toJavaStyleJson(String sObjectJson, Class<?> sObjectClass)
    {
        return toSingleJson(
                doParse(sObjectJson, sObjectClass)
        );
    }

    private static String doParse(String sObjectJson, Class<?> sObjectClass)
    {
        StringBuilder sObjectJsonBuilder = new StringBuilder(sObjectJson);
        Matcher matcher = Pattern.compile(TOTAL_SIZE_REGEX).matcher(sObjectJsonBuilder);
        int totalSize = 0;
        if (matcher.find())
        {
            totalSize = Integer.valueOf(matcher.group(1));
        }

        SObjectMetadata objectMetadata = SObjectRegister.getSObjectMetadata(sObjectClass);
        matcher = Pattern.compile(buildRegex(sObjectClass, objectMetadata.getNonRelationalFields())).matcher(sObjectJsonBuilder);

        StringBuilder javaStyleJsonBuilder = new StringBuilder("[");

        int objectParsed = 0;
        for (int i = 0; i < totalSize; i++)
        {
            objectParsed = 0;
            javaStyleJsonBuilder.append("{");
            matcher = Pattern.compile(buildRegex(sObjectClass, objectMetadata.getNonRelationalFields())).matcher(sObjectJson);

            while (matcher.find())
            {
                String record = matcher.group();
                if (isNameRecord(record) && ++objectParsed > OBJECTS_PER_LOOP)
                {
                    break;
                }

                javaStyleJsonBuilder.append(toJavaStyleField(record));

                System.out.println(sObjectJsonBuilder);

                sObjectJsonBuilder.delete(matcher.start(), matcher.end());
            }

            for (String relationalField : objectMetadata.getRelationalFields())
            {
                matcher = Pattern.compile(RELATIONS_TOTAL_SIZE_REGEX).matcher(sObjectJsonBuilder);
                int relationTotalSize = 0;
                if (matcher.find())
                {
                    relationTotalSize = Integer.valueOf(matcher.group(1));
                }

                Class<?> relationalFieldClass = ParsingUtil.toJavaClass(relationalField);

                boolean isOneLoopNeeded = sObjectJsonBuilder.toString().contains(relationalField);
                if (relationTotalSize > 0 || isOneLoopNeeded)
                {
                    javaStyleJsonBuilder
                            .append("\"")
                            .append(relationalField)
                            .append("\"")
                            .append(" : [");

                    for (int j = 0; j < relationTotalSize || isOneLoopNeeded; j++)
                    {
                        objectParsed = 0;
                        SObjectMetadata relationalFieldMetadata = SObjectRegister.getSObjectMetadata(relationalFieldClass);

                        matcher = Pattern.compile(buildRegex(sObjectClass, relationalFieldMetadata.getNonRelationalFields())).matcher(sObjectJsonBuilder);

                        javaStyleJsonBuilder.append("{");
                        while (matcher.find())
                        {
                            String record = matcher.group();
                            if (isNameRecord(record) && ++objectParsed > OBJECTS_PER_LOOP)
                            {
                                break;
                            }
                            javaStyleJsonBuilder.append(toJavaStyleField(record));
                        }
                        javaStyleJsonBuilder.append("},");
                        isOneLoopNeeded = false;
                    }
                    ParsingUtil.deleteLastComma(javaStyleJsonBuilder);
                    javaStyleJsonBuilder.append("]");
                }
            }
            javaStyleJsonBuilder.append("},");
        }
        ParsingUtil.deleteLastComma(javaStyleJsonBuilder);
        javaStyleJsonBuilder.append("]");
        System.out.println(javaStyleJsonBuilder);
        return javaStyleJsonBuilder.toString();
    }

    private static String buildRegex(Class<?> sObjectClass, List<String> fieldsToSearch)
    {
        StringBuilder regexBuilder = new StringBuilder(NAME_REGEX);

        for (String nonRelationalField : fieldsToSearch)
        {
            regexBuilder
                    .append("|(")
                    .append("\"")
                    .append(nonRelationalField)
                    .append("\"")
                    .append(" : (.*)?(,?)")
                    .append(")");
        }
        return regexBuilder.toString();
    }

    private static boolean isNameRecord(String record)
    {
        return fetchFieldName(record).equals("Name");
    }

    private static String toJavaStyleField(String recordToChange)
    {
        String salesforceFieldName = fetchFieldName(recordToChange);
        return recordToChange.replaceAll(salesforceFieldName, ParsingUtil.toJavaStyleField(salesforceFieldName));
    }

    private static String fetchFieldName(String record)
    {
        Matcher matcher = Pattern.compile(FIELD_NAME_REGEX).matcher(record);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        throw new AssertionError("Could not change fetch field name in record: " + record);
    }

    /**
     * Formats JSON array String to single SON string.
     *
     * @param jsonArrayString JSON array String to format
     * @return formatted single JSON String
     */
    private static String toSingleJson(String jsonArrayString)
    {
        int openingBracket = jsonArrayString.indexOf("[");
        int closingBracket = jsonArrayString.lastIndexOf("]");
        return new StringBuilder(jsonArrayString)
                .delete(openingBracket, openingBracket + 1)
                .delete(closingBracket - 1, closingBracket) //after first deleting index will shift left by one character
                .toString();
    }
}
