package com.softserve.entity.generator.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.lang.reflect.Type;
import java.util.Map;

@Embeddable
public class State
{
    @Column(name = "Is_New")
    private Boolean isNew = true;

    @Column(name = "Is_Deleted")
    private Boolean isDeleted = false;

    @Column(name = "Old_Metadata")
    private String oldMetadata;

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

    public Map<String, String> getOldMetadata()
    {
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        return new Gson().fromJson(oldMetadata, type);
    }

    public void setOldMetadata(Map<String, String> oldMetadata)
    {
        this.oldMetadata = new Gson().toJson(oldMetadata);
    }

    public void resetAfterApply()
    {
        isNew = false;
        isDeleted = false;
        oldMetadata = null;
    }
}
