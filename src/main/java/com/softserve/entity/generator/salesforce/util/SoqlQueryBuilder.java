package com.softserve.entity.generator.salesforce.util;

import com.softserve.entity.generator.salesforce.SObjectRegister;
import com.softserve.entity.generator.salesforce.FetchType;
import com.softserve.entity.generator.salesforce.SObjectMetadata;
import org.apache.log4j.Logger;

public class SoqlQueryBuilder
{
    private static final Logger logger = Logger.getLogger(SoqlQueryBuilder.class);

    public static String buildQuery(Class<?> sObjectClass, FetchType fetchType)
    {
        StringBuilder queryBuilder = new StringBuilder();
        SObjectMetadata objectMetadata = SObjectRegister.getSObjectMetadata(sObjectClass);
        queryBuilder
                .append("SELECT+Name,")
                .append(
                        ParsingUtil.stringifyFieldsList(
                                objectMetadata.getNonRelationalFields()
                        )
                );

        if (fetchType.equals(FetchType.EAGER))
        {
            for (String relation : objectMetadata.getRelationalFields())
            {
                if (ParsingUtil.isChild(relation))
                {
                    SObjectMetadata relatedObjectMetadata = SObjectRegister.getSObjectMetadata(ParsingUtil.toJavaClass(relation));
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
                else
                {
                    String relationPrefix = relation + ".";
                    queryBuilder
                            .append(",")
                            .append(relationPrefix)
                            .append("Name")
                            .append(",")
                            .append(
                                    ParsingUtil.stringifyFieldsList(
                                            SObjectRegister.getSObjectMetadata(ParsingUtil.toJavaClass(relation)).getNonRelationalFields(),
                                            relationPrefix
                                    )
                            );
                }
            }
        }

        queryBuilder
                .append("+FROM+")
                .append(sObjectClass.getSimpleName())
                .append("__c");

        return queryBuilder.toString();
    }
}
