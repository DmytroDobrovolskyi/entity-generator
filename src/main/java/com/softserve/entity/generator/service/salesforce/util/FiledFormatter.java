package com.softserve.entity.generator.service.salesforce.util;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.*;

public class FiledFormatter
{
    private static final Logger logger = Logger.getLogger(FiledFormatter.class);

    public static String toJavaStyle(String jsonToFormat)
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

    public static String toSalesforceStyle(List<Field> fields, String postfix)
    {
        StringBuilder formattedFields = new StringBuilder();
        for (Field field : fields)
        {
            String fieldName = field.getName();

            formattedFields
                    .append(fieldName)
                    .append(postfix)
                    .append(',')
                    .setCharAt(
                            formattedFields.length() - (fieldName.length() + postfix.length()) - 1,
                            toUpperCase(fieldName.charAt(0))
                    );
            logger.info(formattedFields);
        }

        int indexOfComma = formattedFields.lastIndexOf(",");
        if (indexOfComma != -1)
        {
            formattedFields.delete(indexOfComma, indexOfComma + 1);
        }

        return formattedFields.toString();
    }
}
