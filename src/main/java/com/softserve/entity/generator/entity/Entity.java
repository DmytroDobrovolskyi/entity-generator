package com.softserve.entity.generator.entity;


import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "ENTITY")
public class Entity {
    @Id
    private String name;
    private String tableName;

    @OneToMany(cascade = {CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "entity_id")
    private Set<Field> fields;

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
