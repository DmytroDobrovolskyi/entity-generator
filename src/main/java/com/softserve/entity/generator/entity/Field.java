package com.softserve.entity.generator.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "FIELD")
public class Field
{

    @Id
    @Column(name = "Field_Id")
    private String fieldId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Column_Name")
    private String columnName;

    @Column(name = "Type")
    private String type;

    @Embedded
    private State state = new State();

    @ManyToOne
    @JoinColumn(name = "Entity_Id")
    private Entity entity;

    protected Field() {}

    public Field(String name, String columnName, String type)
    {
        this.name = name;
        this.columnName = columnName;
        this.type = type;
    }

    public String getFieldId()
    {
        return fieldId;
    }

    private void setFieldId(String fieldId)
    {
        this.fieldId = fieldId;
    }

    public String getName()
    {
        return name;
    }


    private void setName(String name)
    {
        this.name = name;
    }

    public String getColumnName()
    {
        return columnName;
    }


    private void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getType()
    {
        return type;
    }

    private void setType(String type)
    {
        this.type = type;
    }

    public Entity getEntity()
    {
        return entity;
    }

    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public boolean isChanged(Field field)
    {
        return !(new EqualsBuilder()
                .append(columnName, field.columnName)
                .append(type, field.type)
                .isEquals());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }

        if (o == null || getClass() != o.getClass()) { return false; }

        Field field = (Field) o;

        return new EqualsBuilder()
                .append(fieldId, field.fieldId)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(fieldId)
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return "Field{" +
                "fieldId='" + fieldId + '\'' +
                ", name='" + name + '\'' +
                ", columnName='" + columnName + '\'' +
                ", type='" + type + '\'' +
                ", state=" + state +
                '}';
    }
}
