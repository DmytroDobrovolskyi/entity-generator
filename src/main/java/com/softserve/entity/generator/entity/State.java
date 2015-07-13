package com.softserve.entity.generator.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.lang.reflect.Type;
import java.util.HashMap;
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

    @Transient
    private Map<String, String> oldMetadataMap = new HashMap<String, String>();

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

    private String getOldMetadata()
    {
        return oldMetadata;
    }

    private void setOldMetadata(Map<String, String> oldMetadata)
    {
        this.oldMetadata = new Gson().toJson(oldMetadata);
    }

    public Map<String, String> getOldMetadataMap()
    {
        if(oldMetadata != null)
        {
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            oldMetadataMap =  new Gson().fromJson(oldMetadata, type);
        }

        return oldMetadataMap;
    }

    private void setOldMetadataMap(Map<String, String> oldMetadataMap)
    {
        this.oldMetadataMap = oldMetadataMap;
    }

    public void processOldMetadata()
    {
        setOldMetadata(oldMetadataMap);
    }

    public void resetAfterApply()
    {
        isNew = false;
        isDeleted = false;
        oldMetadata = null;
    }
}
