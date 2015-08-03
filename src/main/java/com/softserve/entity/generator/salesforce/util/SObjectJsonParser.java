package com.softserve.entity.generator.salesforce.util;

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

    public static String toJavaStyleJsonArray(String sObjectJson, Class<?> javaClassAnalogue)
    {
        return doParse(sObjectJson, javaClassAnalogue);
    }

    public static String toJavaStyleJson(String sObjectJson, Class<?> javaClassAnalogue)
    {
        return toSingleJson(
                doParse(sObjectJson, javaClassAnalogue)
        );
    }

    private static String doParse(String jsonToParse, Class<?> javaClassAnalogue)
    {
        StringBuilder javaStyleJsonBuilder = new StringBuilder("[");
        int totalSize = getTotalSize(TOTAL_SIZE_REGEX, jsonToParse);
        if (totalSize > 0)
        {
            Matcher matcher = Pattern.compile(
                    buildRegex(
                            javaClassAnalogue,
                            SObjectRegister.getSObjectMetadata(javaClassAnalogue).getNonRelationalFields()))
                    .matcher(jsonToParse);

            String leftToParse = jsonToParse;
            boolean isAlreadyParsed = false;

            for (int i = 0; i < totalSize; i++)
            {
                javaStyleJsonBuilder
                        .append("{");

                while (matcher.find())
                {
                    String record = matcher.group();
                    boolean isNameField = isName(record);

                    if (isNameField && isAlreadyParsed)
                    {
                        System.out.println(leftToParse);
                        isAlreadyParsed = false;
                        break;
                    }
                    else if (isNameField)
                    {
                        isAlreadyParsed = true;
                    }
                    javaStyleJsonBuilder.append(toJavaStyleRecord(record));
                    leftToParse = leftToParse.replaceFirst(record.trim(), "");
                }
                leftToParse = parseRelationalFields(javaStyleJsonBuilder, leftToParse, javaClassAnalogue);
                matcher.reset(leftToParse);

                javaStyleJsonBuilder.append("},");
            }
            ParsingUtil.deleteLastComma(javaStyleJsonBuilder);

            System.out.println(javaStyleJsonBuilder);
        }
        return javaStyleJsonBuilder
                .append("]")
                .toString();
    }

    private static String parseRelationalFields(StringBuilder javaStyleJsonBuilder, String leftToParse, Class<?> javaClassAnalogue)
    {
        for (String relationalField : SObjectRegister.getSObjectMetadata(javaClassAnalogue).getRelationalFields())
        {
            Class<?> relationClass = ParsingUtil.toJavaClass(relationalField);
            Matcher matcher = Pattern.compile(
                    buildRegex(
                            relationClass,
                            SObjectRegister.getSObjectMetadata(relationClass).getNonRelationalFields()))
                    .matcher(leftToParse);

            int totalSize = getTotalSize(RELATIONS_TOTAL_SIZE_REGEX, leftToParse);
            if (ParsingUtil.isChild(javaClassAnalogue, relationClass) && totalSize > 0)
            {
                javaStyleJsonBuilder
                        .append("\"")
                        .append(ParsingUtil.toJavaStyleField(relationalField))
                        .append("\"")
                        .append(" : [");

                String nextObjectNameRecord = "";
                boolean isAlreadyParsed = false;

                for (int i = 0; i < totalSize; i++)
                {
                    javaStyleJsonBuilder
                            .append("{")
                            .append(toJavaStyleRecord(nextObjectNameRecord));
                    leftToParse = leftToParse.replaceFirst(nextObjectNameRecord.trim(), "");

                    while (matcher.find())
                    {
                        String record = matcher.group();
                        boolean isNameField = isName(record) || isName(nextObjectNameRecord);
                        if (isNameField && isAlreadyParsed)
                        {
                            nextObjectNameRecord = record;
                            isAlreadyParsed = false;
                            break;
                        }
                        else if (isNameField)
                        {
                            nextObjectNameRecord = "";
                            isAlreadyParsed = true;
                        }
                        javaStyleJsonBuilder.append(toJavaStyleRecord(record));
                        leftToParse = leftToParse.replaceFirst(record.trim(), "");
                    }
                    javaStyleJsonBuilder.append("},");
                }
                ParsingUtil.deleteLastComma(javaStyleJsonBuilder);
                javaStyleJsonBuilder.append("]");
            }
            else if (leftToParse.contains(relationalField))
            {
                javaStyleJsonBuilder
                        .append("\"")
                        .append(ParsingUtil.toJavaStyleField(relationalField))
                        .append("\"")
                        .append(" : { ");

                boolean isAlreadyParsed = false;
                while (matcher.find())
                {
                    String record = matcher.group();
                    boolean isNameField = isName(record);
                    if (isNameField && isAlreadyParsed)
                    {
                        isAlreadyParsed = false;
                        break;
                    }
                    else if (isNameField)
                    {
                        isAlreadyParsed = true;
                    }
                    javaStyleJsonBuilder.append(toJavaStyleRecord(record));
                    leftToParse = leftToParse.replaceFirst(record.trim(), "");
                }
                javaStyleJsonBuilder.append("}");
            }
        }
        return leftToParse;
    }

    private static int getTotalSize(String regex, String json)
    {
        Matcher matcher = Pattern.compile(regex).matcher(json);
        int totalSize = 0;
        if (matcher.find())
        {
            totalSize = Integer.valueOf(matcher.group(1));
        }
        return totalSize;
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

    private static boolean isName(String record)
    {
        return fetchFieldName(record).equals("Name");
    }

    private static String toJavaStyleRecord(String recordToChange)
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
        return "";
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
