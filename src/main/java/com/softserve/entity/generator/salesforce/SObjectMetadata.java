package com.softserve.entity.generator.salesforce;

import java.util.List;

/**
 * Class-holder that serves for entities fields distinction
 */
public class SObjectMetadata
{
    private final List<String> nonRelationalFields;
    private final List<String> relationalFields;

    public SObjectMetadata(List<String> nonRelationalFields, List<String> relationalFields)
    {
        this.nonRelationalFields = nonRelationalFields;
        this.relationalFields = relationalFields;
    }

    public List<String> getNonRelationalFields()
    {
        return nonRelationalFields;
    }

    public List<String> getRelationalFields()
    {
        return relationalFields;
    }
}
