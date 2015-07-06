package com.softserve.entity.generator.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "FIELD")
public class Field {

    @Id
    @Column(name = "Field_Id")
    private String fieldId;

    @Column(name = "Name")
    private String name;


    @Column(name="Column_Name")
    private String columnName;

    @Column(name = "Type")
    private String type;

    @ManyToOne
    private Entity entity;

    public String getFieldId()
    {
        return fieldId;
    }

    public void setFieldId(String fieldId)
    {
        this.fieldId = fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }


    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", columnName='" + columnName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        return new EqualsBuilder()
                .append(name, field.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }
}
