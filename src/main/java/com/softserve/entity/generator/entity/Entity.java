package com.softserve.entity.generator.entity;


import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "ENTITY")
public class Entity {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Table_Name")
    private String tableName;

    @OneToMany(cascade = {CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "Entity_Id")
    private Set<Field> fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }
}
