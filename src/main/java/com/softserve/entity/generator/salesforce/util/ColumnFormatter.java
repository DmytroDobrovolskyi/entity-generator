package com.softserve.entity.generator.salesforce.util;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.toLowerCase;

public class ColumnFormatter
{
    private static final Logger logger = Logger.getLogger(ColumnFormatter.class);

    /**
     * Converts JSON String that contains set of salesforce-style columns to java-style columns.
     *
     * @param jsonToFormat JSON String
     * @return formatted JSON String
     */
    public static String JsonToJavaStyle(String jsonToFormat)
    {
        StringBuilder formattedJson = new StringBuilder(jsonToFormat);
        Matcher matcher = Pattern.compile("(\"[A-Z].*__c\")|(\"[A-Z].*__r\")|(\"Name\")")
                .matcher(formattedJson);

        while (matcher.find())
        {
            formattedJson.setCharAt(
                    matcher.start() + 1,
                    toLowerCase(
                            formattedJson
                            .charAt(matcher.start() + 1)
                    )
            );
        }
        return formattedJson
                .toString()
                .replaceAll("__c", "")
                .replaceAll("__r. :", "\" : \\[ \\{");
    }

    /**
     * Converts java.lang.reflect.Field to salesforce-like stringified field
     *
     * @param field field to convert
     * @param postfix extension like __c or __r
     * @return formatted stringified field in salesforce style
     */
    public static String toSalesforceStyle(Field field, String postfix)
    {
        String javaStyleField = field.getName();
        return  new StringBuilder(javaStyleField)
                .replace(0, 1, (javaStyleField.charAt(0) + "").toUpperCase())
                .append(postfix)
                .toString();
    }

    /**
     * Converts List of stringified salesforce-style fields to single comma-separated stringified fields.
     *
     * @param fields stringified salesforce-style fields to process
     * @return single comma-separated salesforce-style stringified fields
     */
    public static String stringifyFieldsList(List<String> fields)
    {
        StringBuilder fieldStringBuilder = new StringBuilder();
        for (String field : fields)
        {
            fieldStringBuilder
                    .append(field)
                    .append(",");
        }
        int lastCommaIndex = fieldStringBuilder.length() - 1 ;
        return fieldStringBuilder
                .delete(lastCommaIndex, lastCommaIndex + 1)
                .toString();
    }
}
