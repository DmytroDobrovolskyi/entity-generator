package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.ColumnsRegister;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.toLowerCase;

/**
 * Class that helps in parsing different data in classes like SooqlQueryBuilder, Parser, ColumnsRegisterProcessor.
 */
public class ParsingUtil
{
    private static final Logger logger = Logger.getLogger(ParsingUtil.class);

    /**
     * Checks if SObject is child in salesforce database relationships by its name.
     *
     * @param sObjectName name of SObject
     * @return true if sObjectClassName is child in salesforce database relationships, false otherwise
     */
    public static boolean isChild(String sObjectName)
    {
        return sObjectName.endsWith("s__r");
    }

    /**
     * Converts salesforce-style classname to Java Class.
     * Note: raw type was used intentionally because of webservice issue.
     *
     * @param sObjectClassName salesforce-style classname
     * @return corresponding java Class object
     */
    public static Class toJavaClass(String sObjectClassName)
    {
        String javaStyleClassName = sObjectClassName.replaceAll("(__c)|(s__r)|(__r)", "");
        try
        {
            return Class.forName(ColumnsRegister.getFullName(javaStyleClassName));
        }
        catch (ClassNotFoundException ex)
        {
            logger.error("Cannot map SObject class " + sObjectClassName + " to Java class");
            throw new AssertionError(ex);
        }
    }

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
     * @param field   field to convert
     * @param postfix extension like __c or __r
     * @return formatted stringified {@literal field} in salesforce style
     */
    public static String toSalesforceStyle(Field field, String postfix)
    {
        String javaStyleField = field.getName();
        return new StringBuilder(javaStyleField)
                .replace(0, 1, (javaStyleField.charAt(0) + "").toUpperCase())
                .append(postfix)
                .toString();
    }

    /**
     * Converts List of stringified salesforce-style fields to single comma-separated stringified fields.
     *
     * @param fields stringified salesforce-style fields to process
     * @return single comma-separated salesforce-style stringified {@literal fields}
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
        int lastCommaIndex = fieldStringBuilder.length() - 1;
        return fieldStringBuilder
                .delete(lastCommaIndex, lastCommaIndex + 1)
                .toString();
    }
}
