package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.salesforce.util.ObjectType;

import java.util.List;

/**
 * Class-holder that serves for entities fields distinction
 */
public class SObjectMetadata
{
    private final List<String> nonRelationalFields;
    private final List<String> relationalFields;
    private final ObjectType objectType;

    public SObjectMetadata(List<String> nonRelationalFields, List<String> relationalFields, ObjectType objectType)
    {
        this.nonRelationalFields = nonRelationalFields;
        this.relationalFields = relationalFields;
        this.objectType = objectType;
    }

    public List<String> getNonRelationalFields()
    {
        return nonRelationalFields;
    }

    public List<String> getRelationalFields()
    {
        return relationalFields;
    }

    public ObjectType getObjectType()
    {
        return objectType;
    }
}
