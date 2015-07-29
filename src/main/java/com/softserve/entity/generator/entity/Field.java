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

    @Column(name = "Is_Primary_Key")
    private Boolean isPrimaryKey;

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

    public Boolean getIsPrimaryKey()
    {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey)
    {
        this.isPrimaryKey = isPrimaryKey;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }

        if (o == null || getClass() != o.getClass()) { return false; }

        Field field = (Field) o;

        return new EqualsBuilder()
                .append(fieldId, field.fieldId)
                .append(columnName, field.columnName)
                .append(type, field.type)
                .append(isPrimaryKey, field.isPrimaryKey)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(fieldId)
                .toHashCode();
    }
}
