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

    private static final Logger logger = Logger.getLogger(EntityChangesTracker.class);

    public void trackChanges(List<Entity> entitiesToTrack)
    {
        Map<String, Entity> managedEntities = new HashMap<String, Entity>();
        for (Entity entity : entityService.findAll())
        {
            managedEntities.put(entity.getEntityId(), entity);
        }

        for (Entity entity : entitiesToTrack)
        {
            String id = entity.getEntityId();
            if (managedEntities.containsKey(id))
            {
                Entity managedEntity = managedEntities.get(id);
                State state = entity.getState();

                boolean isChanged = entity.isChanged(managedEntity);
                if (isChanged)
                {
                    state.setIsNameChanged(true);
                    state.setOldName(managedEntity.getTableName());
                }

                state.setIsNew(false);
            }
            managedEntities.remove(id);
        }

        for (String id : managedEntities.keySet())
        {
            managedEntities
                    .get(id)
                    .getState().setIsDeleted(true);
        }
    }
}
