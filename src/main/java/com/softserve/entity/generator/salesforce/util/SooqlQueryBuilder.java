package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.ColumnsRegister;
import com.softserve.entity.generator.salesforce.SObjectMetadata;
import org.apache.log4j.Logger;

public class SooqlQueryBuilder
{
    private static final Logger logger = Logger.getLogger(SooqlQueryBuilder.class);

    public static String buildQuery(Class<?> sObjectClass)
    {
        StringBuilder queryBuilder  = new StringBuilder();
        SObjectMetadata objectMetadata = ColumnsRegister.getSObjectMetadata(sObjectClass);
        queryBuilder
                .append("SELECT+Name,")
                .append(
                        ColumnFormatter.stringifyFieldsList(
                                objectMetadata.getNonRelationalFields()
                        )
                );

        for (String relation : objectMetadata.getRelationalFields())
        {
            if(isParent(relation))
            {
                SObjectMetadata relatedObjectMetadata = ColumnsRegister.getSObjectMetadata(toJavaClass(relation));
                queryBuilder
                        .append(",(SELECT+Name,")
                        .append(
                                ColumnFormatter.stringifyFieldsList(
                                        relatedObjectMetadata.getNonRelationalFields()
                                )
                        )
                        .append("FROM+")
                        .append(relation)
                        .append(")");
            }
        }

        queryBuilder
                .append("+FROM+")
                .append(sObjectClass.getSimpleName())
                .append("__c");

         return queryBuilder.toString();
    }

    private static boolean isParent(String relation)
    {
        return relation.endsWith("s__r");
    }

    private static Class<?> toJavaClass(String sObjectName)
    {
        String javaStyleClassName = sObjectName.replace("s__r", "");
        try
        {
            return Class.forName(ColumnsRegister.getFullName(javaStyleClassName));
        }
        catch (ClassNotFoundException ex)
        {
            logger.error("Class not found: " + javaStyleClassName, ex);
            throw new AssertionError(ex);
        }
    }
}
