package com.softserve.entity.generator.entity;

import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "ENTITY")
public class Entity
{
    @Id
    @Column(name = "Table_Name")
    private String tableName;

    @Column(name = "Name")
    private String name;

    @OneToMany(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "Entity_Id")
    private Set<Field> fields;

    protected Entity() {}

    public Entity(String tableName, String name)
    {
        this.tableName = tableName;
        this.name = name;
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
}
