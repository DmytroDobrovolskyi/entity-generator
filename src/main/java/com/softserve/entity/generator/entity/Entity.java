package com.softserve.entity.generator.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "ENTITY")
public class Entity
{
    @Id
    @Column(name = "Entity_Id")
    private String entityId;

    @Column(name = "Table_Name")
    private String tableName;

    @Column(name = "Name")
    private String name;

    @Column(name = "Processing_Is_Needed")
    private Boolean processingIsNeeded = false;

    @OneToMany(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "Entity_Id")
    private Set<Field> fields = new HashSet<Field>();

    protected Entity() {}

    public Entity(String entityId, String name)
    {
        this.entityId = entityId;
        this.name = name;
    }

    public String getEntityId()
    {
        return entityId;
    }

    public void setEntityId(String entityId)
    {
        this.entityId = entityId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public Set<Field> getFields()
    {
        return fields;
    }

    public void setFields(Set<Field> fields)
    {
        this.fields = fields;
    }

    public Boolean getProcessingIsNeeded()
    {
        return processingIsNeeded;
    }

    public void setProcessingIsNeeded(Boolean processingIsNeeded)
    {
        this.processingIsNeeded = processingIsNeeded;
    }

    public boolean isChanged(Entity entity)
    {
        boolean isChanged = tableName.equals(entity.tableName);
        if (!isChanged)
        {
            List<Field> managedFields = new ArrayList<Field>(entity.getFields());
            List<Field> transientFields = new ArrayList<Field>(getFields());
            if (managedFields.size() != transientFields.size())
            {
                return false;
            }
            for (int i = 0; i < managedFields.size(); i++)
            {
                if (managedFields.get(i).isChanged(transientFields.get(i)))
                {
                    return true;
                }
            }
        }

        return isChanged;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }

        if (o == null || getClass() != o.getClass()) { return false; }

        Entity entity = (Entity) o;

        return new EqualsBuilder()
                .append(entityId, entity.entityId)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(entityId)
                .toHashCode();
    }
}
