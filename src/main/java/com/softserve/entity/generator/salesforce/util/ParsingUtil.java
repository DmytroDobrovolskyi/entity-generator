package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.salesforce.SObjectRegister;
import com.softserve.entity.generator.util.ReflectionUtil;
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
     * Checks if SObject is child in salesforce database relationships.
     *
     * @param objectClassToCheck object class where to check
     * @param relationClass      field class to check
     * @return true if sObjectClassName is child in salesforce database relationships, false otherwise
     */
    public static boolean isChild(Class<?> objectClassToCheck, Class<?> relationClass)
    {
        return ReflectionUtil.isMethodExist(objectClassToCheck, "get" + relationClass.getSimpleName() + "s");
    }

    /**
     * Converts salesforce-style classname to Java Class.
     * Note: raw type was used intentionally because of webservice issue.
     *
     * @param sObjectClassName salesforce-style classname
     * @return corresponding java Class object
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> toJavaClass(String sObjectClassName)
    {
        String javaStyleClassName = sObjectClassName.replaceAll("(__c)|(s__r)|(__r)", "");
        try
        {
            return (Class<T>) Class.forName(SObjectRegister.getFullName(javaStyleClassName)).asSubclass(DatabaseObject.class);
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
    public static String toJavaStyleJson(String jsonToFormat)
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
    public static String toSalesforceStyleField(Field field, String postfix)
    {
        String javaStyleField = field.getName();
        return new StringBuilder(javaStyleField)
                .replace(0, 1, (javaStyleField.charAt(0) + "").toUpperCase())
                .append(postfix)
                .toString();
    }

    public static String toJavaStyleField(String salesforceStyleName)
    {
        char firstLetter = salesforceStyleName.charAt(0);
        return salesforceStyleName
                .replace(firstLetter, Character.toLowerCase(firstLetter))
                .replaceAll("(__c)|(__r)", "");
    }

    /**
     * Converts List of stringified salesforce-style fields to single comma-separated stringified fields.
     *
     * @param fields stringified salesforce-style fields to process
     * @return single comma-separated salesforce-style stringified {@literal fields}
     */
    public static String stringifyFieldsList(List<String> fields)
    {
        return stringifyList(fields, "", "");
    }

    public static String stringifyFieldsList(List<String> fields, String prefix)
    {
        return stringifyList(fields, prefix, "");
    }

    public static String stringifyFieldsList(List<String> fields, String prefix, String postfix)
    {
        return stringifyList(fields, prefix, postfix);
    }

    public static void deleteLastComma(StringBuilder stringBuilder)
    {
        int lastCommaIndex = stringBuilder.lastIndexOf(",");
        if (lastCommaIndex == stringBuilder.length() - 1)
        {
            stringBuilder.delete(lastCommaIndex, lastCommaIndex + 1);
        }
    }

    private static String stringifyList(List<String> listToStringify, String prefix, String postfix)
    {
        StringBuilder fieldStringBuilder = new StringBuilder();
        for (String elem : listToStringify)
        {
            fieldStringBuilder
                    .append(prefix)
                    .append(elem)
                    .append(postfix)
                    .append(",");
        }
        deleteLastComma(fieldStringBuilder);
        return fieldStringBuilder.toString();
    }
}
