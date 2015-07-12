package com.softserve.entity.generator.service.salesforce;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.entity.State;
import com.softserve.entity.generator.service.EntityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FieldChangesTracker
{
    private static final Logger logger = Logger.getLogger(FieldChangesTracker.class);

    @Autowired
    private EntityService entityService;

    public void trackChanges(Entity transientOwner, Entity managedOwner)
    {
        Map<String, Field> managedFields = new HashMap<String, Field>();
        for (Field managedField : managedOwner.getFields())
        {
            managedFields.put(managedField.getFieldId(), managedField);
        }

        for (Field transientField : transientOwner.getFields())
        {
            String id = transientField.getFieldId();
            if (managedFields.containsKey(id))
            {
                Field managedField = managedFields.get(id);
                State state = transientField.getState();

                if (managedField.isChanged(transientField))
                {
                    Map<String, String> oldMetadata = new HashMap<String, String>();
                    oldMetadata.put("oldColumnName", managedField.getColumnName());
                    oldMetadata.put("oldType", managedField.getType());
                    state.setOldMetadata(oldMetadata);
                }
                state.setIsNew(false);
            }
            managedFields.remove(id);
        }

        resolveDeleted(managedFields, transientOwner);
        entityService.merge(managedOwner);
    }

    private void resolveDeleted(Map<String, Field> managedFields, Entity transientOwner)
    {
        for (String id : managedFields.keySet())
        {
            Field managedField = managedFields.get(id);
            State fieldState = managedField.getState();

            fieldState.setIsDeleted(true);
            fieldState.setIsNew(false);

            transientOwner.getFields().add(managedField);
        }
    }
}
