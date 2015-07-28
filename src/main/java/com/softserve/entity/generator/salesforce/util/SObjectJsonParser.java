package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.ColumnsRegister;
import com.softserve.entity.generator.salesforce.SObjectMetadata;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.toLowerCase;

public class SObjectJsonParser
{
    private static final Logger logger = Logger.getLogger(SObjectJsonParser.class);

    public static String parseSObjectJsonArray(String sObjectJson, Class<?> sObjectClass)
    {
        return doParse(sObjectJson, sObjectClass, buildRegex(sObjectClass));
    }

    public static String parseSObjectJson(String sObjectJson, Class<?> sObjectClass)
    {
        return toSingleJson(
                doParse(sObjectJson, sObjectClass, buildRegex(sObjectClass))
        );
    }

    private static String doParse(String sObjectJson, Class<?> sObjectClass, String parsingRegex)
    {
        StringBuilder javaStyleJsonBuilder = new StringBuilder("\n[");

        Matcher matcher = Pattern.compile(parsingRegex).matcher(sObjectJson);

        int entitiesQuantity = 0;

        while (matcher.find())
        {
            if (matcher.group().trim().equals("\"type\" : \"" + sObjectClass.getSimpleName() + "__c\","))
            {
                if (entitiesQuantity == 0)
                {
                    javaStyleJsonBuilder.append("{\n");
                }
                else
                {
                    javaStyleJsonBuilder.append("}, {\n");
                }
                entitiesQuantity++;
            }
            else if (matcher.group().trim().contains("__r\" : null"))
            {
                javaStyleJsonBuilder.append("},\n {");
            }
            else
            {
                javaStyleJsonBuilder.append(matcher.group());
            }
        }
        matcher = Pattern.compile("(\"[A-Z].*__c\")|(\"[A-Z].*__r\")|(\"Name\")").matcher(javaStyleJsonBuilder);

        javaStyleJsonBuilder.append("}]\n}]");

        while (matcher.find())
        {
            javaStyleJsonBuilder.setCharAt(
                    matcher.start() + 1,
                    toLowerCase(
                            javaStyleJsonBuilder.charAt(matcher.start() + 1)
                    )
            );
        }

        return javaStyleJsonBuilder.toString()
                .replaceAll("\\}, \\{\\}, \\{", "}]},\n {")
                .replaceAll("__r\" : \\{\\n", "\" : [ {\n")
                .replaceAll("__c", "")
                .replaceAll(",\\n.*},\\n.*\\{\\}\\]\\},", "},")
                .replaceAll(",\\n.*},\\n.*\\{}]", "");
    }

    private static String buildRegex(Class<?> sObjectClass)
    {
        StringBuilder regexBuilder = new StringBuilder();

        SObjectMetadata objectMetadata = ColumnsRegister.getSObjectMetadata(sObjectClass);

        //appends non-relational fields
        for (String nonRelationalField : objectMetadata.getNonRelationalFields())
        {
            regexBuilder
                    .append("(\"")
                    .append(nonRelationalField)
                    .append("\".*\\n)|");
        }
        //appends relational fields
        for (String relationalField : objectMetadata.getRelationalFields())
        {
            if (ParsingUtil.isChild(relationalField))
            {
                SObjectMetadata childMetadata = ColumnsRegister.getSObjectMetadata(ParsingUtil.toJavaClass(relationalField));
                for (String childNonRelationFiled : childMetadata.getNonRelationalFields())
                {
                    regexBuilder
                            .append("(\"")
                            .append(childNonRelationFiled)
                            .append("\".*\\n)|");
                }
            }
        }

        //appends common data
        regexBuilder
                .append("(\"type\" : \"")
                .append(sObjectClass.getSimpleName())
                .append("__c\",\\n)")
                .append("|(\"Name\".*\\n)")
                .append("|(\"[A-Z].*__r\" : \\{\\n)")
                .append("|(\\}, \\{)")
                .append("|(\"[A-Z].*__r\" : null\\n)|(\\}, \\{)");

        return regexBuilder.toString();
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
        logger.info(closingBracket);
        return new StringBuilder(jsonArrayString)
                .delete(openingBracket, openingBracket + 1)
                .delete(closingBracket - 1, closingBracket) //after first deleting index will shift left by one character
                .toString();
    }
}
