package com.softserve.entity.generator.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class State
{
    @Column(name = "Is_New")
    private Boolean isNew = true;

    @Column(name = "Is_Deleted")
    private Boolean isDeleted = false;

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

    public void resetAfterApply()
    {
        isNew = false;
        isDeleted = false;
        oldName = null;
    }

    @Override
    public String toString()
    {
        return "State{" +
                "isNew=" + isNew +
                ", isDeleted=" + isDeleted +
                ", oldName='" + oldName + '\'' +
                '}';
    }
}
