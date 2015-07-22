package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.ColumnsRegister;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.toLowerCase;

public class Parser
{
    private static final Logger logger = Logger.getLogger(Parser.class);

    public static String parseSObjectJson(String sObjectJson, Class entityClass, Class relationClass)
    {
        return parseJson(sObjectJson, entityClass, fieldsRegexFinder(entityClass, relationClass));
    }

    public static String parseSObjectJson(String sObjectJson, Class entityClass)
    {
        return parseJson(sObjectJson, entityClass, fieldsRegexFinder(entityClass));
    }

    private static String parseJson(String sObjectJson, Class entityClass, StringBuilder customFieldsFinder)
    {
        StringBuilder parsedJson = new StringBuilder();
        parsedJson.append("\n[");

        Matcher matcher = Pattern.compile(customFieldsFinder.toString())
                .matcher(sObjectJson);

        int entitiesQuantity = 0;

        while (matcher.find())
        {
            if (matcher.group().trim().equals("\"type\" : \"" + entityClass.getSimpleName() + "__c\","))
            {
                if (entitiesQuantity == 0)
                {
                    parsedJson.append("{\n");
                }
                else
                {
                    parsedJson.append("}, {\n");
                }
                entitiesQuantity++;
            }
            else if (matcher.group().trim().contains("__r\" : null"))
            {
                parsedJson.append("},\n {");
            }
            else
            {
                parsedJson.append(matcher.group());
            }
        }
        matcher = Pattern.compile("(\"[A-Z].*__c\")|(\"[A-Z].*__r\")|(\"Name\")")
                .matcher(parsedJson);

        parsedJson.append("}]\n}]");

        while (matcher.find())
        {
            parsedJson.setCharAt(
                    matcher.start() + 1,
                    toLowerCase(
                            parsedJson
                                    .charAt(matcher.start() + 1)
                    )
            );
        }

        return parsedJson.toString()
                .replaceAll("\\}, \\{\\}, \\{", "}]},\n {")
                .replaceAll("__r\" : \\{\\n", "\" : [ {\n")
                .replaceAll("__c", "")
                .replaceAll(",\\n.*},\\n.*\\{\\}\\]\\},", "},")
                .replaceAll(",\\n.*},\\n.*\\{}]", "");

    }

    private static StringBuilder fieldsRegexFinder(Class entityClass, Class relationClass)
    {
        StringBuilder customAndRelationCustomFieldFinder = new StringBuilder();

        for (String customFields : ColumnsRegister.getCustomFieldsMap().get(entityClass).split(","))
        {
            customAndRelationCustomFieldFinder
                    .append("(\"")
                    .append(customFields)
                    .append("\".*\\n)|");
        }

        for (String relationCustomFields : ColumnsRegister.getCustomFieldsMap().get(relationClass).split(","))
        {
            customAndRelationCustomFieldFinder
                    .append("(\"")
                    .append(relationCustomFields)
                    .append("\".*\\n)|");
        }
        customAndRelationCustomFieldFinder
                .append("(\"type\" : \"")
                .append(entityClass.getSimpleName())
                .append("__c\",\\n)")
                .append("|(\"Name\".*\\n)")
                .append("|(\"[A-Z].*__r\" : \\{\\n)")
                .append("|(\\}, \\{)")
                .append("|(\"[A-Z].*__r\" : null\\n)|(\\}, \\{)");

        return customAndRelationCustomFieldFinder;
    }

    private static StringBuilder fieldsRegexFinder(Class entityClass)
    {
        StringBuilder customAndRelationCustomFieldFinder = new StringBuilder();

        for (String customFields : ColumnsRegister.getCustomFieldsMap().get(entityClass).split(","))
        {
            customAndRelationCustomFieldFinder
                    .append("(\"")
                    .append(customFields)
                    .append("\".*\\n)|");
        }

        customAndRelationCustomFieldFinder
                .append("(\"type\" : \"")
                .append(entityClass.getSimpleName())
                .append("__c\",\\n)").append("|(\"Name\".*\\n)")
                .append("|(\"[A-Z].*__r\" : \\{\\n)").append("|(\\}, \\{)")
                .append("|(\"[A-Z].*__r\" : null\\n)|(\\}, \\{)");

        return customAndRelationCustomFieldFinder;
    }
}
