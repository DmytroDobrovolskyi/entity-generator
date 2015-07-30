package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.ColumnsRegister;
import com.softserve.entity.generator.salesforce.SObjectMetadata;
import org.apache.log4j.Logger;

public class SoqlQueryBuilder
{
    private static final Logger logger = Logger.getLogger(SoqlQueryBuilder.class);

    public static String buildQuery(Class<?> sObjectClass)
    {
        StringBuilder queryBuilder  = new StringBuilder();
        SObjectMetadata objectMetadata = ColumnsRegister.getSObjectMetadata(sObjectClass);
        queryBuilder
                .append("SELECT+Name,")
                .append(
                        ParsingUtil.stringifyFieldsList(
                                objectMetadata.getNonRelationalFields()
                        )
                );

        for (String relation : objectMetadata.getRelationalFields())
        {
            if(ParsingUtil.isChild(relation))
            {
                SObjectMetadata relatedObjectMetadata = ColumnsRegister.getSObjectMetadata(ParsingUtil.toJavaClass(relation));
                queryBuilder
                        .append(",(SELECT+Name,")
                        .append(
                                ParsingUtil.stringifyFieldsList(
                                        relatedObjectMetadata.getNonRelationalFields()
                                )
                        )
                        .append("+FROM+")
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
}
