package com.softserve.entity.generator.service.salesforce;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.State;
import com.softserve.entity.generator.service.EntityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityChangesTracker
{
    @Autowired
    private EntityService entityService;

    @Autowired
    private FieldChangesTracker fieldChangesTracker;

    private static final Logger logger = Logger.getLogger(EntityChangesTracker.class);

    public void trackChanges(List<Entity> entitiesToTrack)
    {
        Map<String, Entity> managedEntities = new HashMap<String, Entity>();
        for (Entity entity : entityService.findAll())
        {
            managedEntities.put(entity.getEntityId(), entity);
        }

        for (Entity transientEntity : entitiesToTrack)
        {
            String id = transientEntity.getEntityId();
            Entity managedEntity = managedEntities.get(id);

            if (managedEntities.containsKey(id))
            {
                State state = transientEntity.getState();

                if (managedEntity.isChanged(transientEntity))
                {
                    state.setOldName(managedEntity.getTableName());
                }

                if (managedEntity.getFields().size() != 0)
                {
                    state.setIsNew(false);
                }

                fieldChangesTracker.trackChanges(transientEntity, managedEntity);
            }
            managedEntities.remove(id);
        }
        resolveDeleted(managedEntities);
    }

    private void resolveDeleted(Map<String, Entity> managedEntities)
    {
        for (String id : managedEntities.keySet())
        {
            Entity entity = managedEntities.get(id);
            State entityState = entity.getState();

            entityState.setIsDeleted(true);
            entityState.setIsNew(false);

            entityService.merge(entity);
        }
    }
}
