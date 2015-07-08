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

    @Embedded
    private State state = new State();

    @Column(name = "Is_Fields_Changed")
    private Boolean isFieldsChanged = false;

    @OneToMany(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "Entity_Id")
    private Set<Field> fields = new HashSet<Field>();

    protected Entity() {}

    public Entity(String entityId, String name, String tableName)
    {
        this.entityId = entityId;
        this.name = name;
        this.tableName = tableName;
    }

    public String getEntityId()
    {
        return entityId;
    }

    private void setEntityId(String entityId)
    {
        this.entityId = entityId;
    }

    public String getName()
    {
        return name;
    }

    private void setName(String name)
    {
        this.name = name;
    }

    public String getTableName()
    {
        return tableName;
    }

    private void setTableName(String tableName)
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

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public Boolean getIsFieldsChanged()
    {
        return isFieldsChanged;
    }

    public void setIsFieldsChanged(Boolean isFieldsChanged)
    {
        this.isFieldsChanged = isFieldsChanged;
    }

    public boolean isChanged(Entity entity)
    {
       /* List<Field> managedFields = new ArrayList<Field>(entity.getFields());
        List<Field> transientFields = new ArrayList<Field>(getFields());
        int managedFieldsSize = managedFields.size();
        int transientFieldsSize = transientFields.size();

        int bound = managedFieldsSize >= transientFieldsSize ? managedFieldsSize : transientFieldsSize;

        for (int i = 0; i < bound && i != managedFieldsSize && i != transientFieldsSize; i++)
        {
            Field managedField = managedFields.get(i);
            Field transientField = transientFields.get(i);

            if (managedField.isChanged(transientField))
            {
                transientField.getState().set
            }
        }*/

        return !tableName.equals(entity.tableName);
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

    @Override
    public String toString()
    {
        return "Entity{" +
                "entityId='" + entityId + '\'' +
                ", tableName='" + tableName + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", isFieldsChanged=" + isFieldsChanged +
                ", fields=" + fields +
                '}';
    }
}
