package com.softserve.entity.generator.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class State
{
    @Column(name = "Is_New")
    private Boolean isNew;

    @Column(name = "Is_Name_Changed")
    private Boolean isNameChanged;

    @Column(name = "Is_Deleted")
    private Boolean isDeleted;

    @Column(name = "Old_Name")
    private String oldName;

    public Boolean getIsNew()
    {
        return isNew;
    }

    public void setIsNew(Boolean newTable)
    {
        this.isNew = newTable;
    }

    public Boolean getIsNameChanged()
    {
        return isNameChanged;
    }

    public void setIsNameChanged(Boolean tableNameIsChanges)
    {
        this.isNameChanged = tableNameIsChanges;
    }

    public Boolean getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public String getOldName()
    {
        return oldName;
    }

    public void setOldName(String oldName)
    {
        this.oldName = oldName;
    }
}
