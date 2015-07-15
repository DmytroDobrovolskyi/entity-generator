package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.repository.EntityRepository;
import com.softserve.entity.generator.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class EntityServiceImpl extends BaseServiceImpl<Entity> implements EntityService
{

    @Autowired
    private EntityRepository entityRepository;

    @Override
    @Transactional
    public void resolveDeleted(List<Entity> entities)
    {
        for (Entity managedEntity : entityRepository.findAll())
        {
            if (!entities.contains(managedEntity))
            {
                entityRepository.delete(managedEntity);
            }
        }
    }

    @Override
    @Transactional
    public void trackChanges(List<Entity> receivedEntities)
    {
        Map<String, Entity> managedEntitiesMap = new HashMap<String, Entity>();
        for (Entity managedEntity : entityRepository.findAll())
        {
            managedEntitiesMap.put(managedEntity.getEntityId(), managedEntity);
        }

        for (Entity receivedEntity : receivedEntities)
        {
            String id = receivedEntity.getEntityId();
            if (managedEntitiesMap.containsKey(id))
            {
                Entity managedEntity = managedEntitiesMap.get(id);

                receivedEntity.setIsProcessingNeeded(
                        isFieldsChanged(receivedEntity.getFields(), managedEntity.getFields())
                );
            }
        }
    }

    private boolean isFieldsChanged(Set<Field> receivedFields, Set<Field> managedFields)
    {
        Map<String, Field> managedFieldsMap = new HashMap<String, Field>();
        for (Field managedField : managedFields)
        {
            managedFieldsMap.put(managedField.getFieldId(), managedField);
        }

        for (Field receivedField : receivedFields)
        {
            String id = receivedField.getFieldId();
            if (managedFieldsMap.containsKey(id))
            {
                return !managedFieldsMap.get(id)
                        .equals(receivedField);
            }
        }
        return false;
    }
}
