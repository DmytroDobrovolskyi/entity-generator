package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.salesforce.util.ObjectType;
import com.softserve.entity.generator.salesforce.util.ParsingUtil;
import com.softserve.entity.generator.service.BaseService;
import com.softserve.entity.generator.webservice.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SObjectSynchronizer
{
    public static List<DatabaseObject> syncObjects(Map<String, ? extends DatabaseObject> objectsToSync, OperationType operationType)
    {
        switch (operationType)
        {
            case INSERT_OPERATION:
            case UPDATE_OPERATION:
                return syncOnInsertUpdate(objectsToSync);
        }
        return null;
    }

    private static <T extends DatabaseObject> List<DatabaseObject> syncOnInsertUpdate(Map<String, T> objectsToSyncMap)
    {
        List<DatabaseObject> synchronizedObjects = new ArrayList<DatabaseObject>();

        for (Map.Entry<String, T> objectEntry : objectsToSyncMap.entrySet())
        {
            T objectToSync = objectEntry.getValue();
            Class<? extends DatabaseObject> objectClass = objectToSync.getClass();

            @SuppressWarnings("unchecked")
            BaseService<T> baseService = AppContextCache.getContext(AppConfig.class).getBean(BaseService.class);

            SObjectMetadata objectMetadata = SObjectRegister.getSObjectMetadata(objectClass);
            ObjectType objectType = objectMetadata.getObjectType();

            if (objectType.equals(ObjectType.SUBORDER_OBJECT))
            {
                for (String parentName : objectMetadata.getRelationalFields())
                {
                    DatabaseObject parent = baseService.findById(objectEntry.getKey());

                    Set<DatabaseObject> childrenSet = parent.getChildren(objectClass);

                    if (childrenSet.remove(objectToSync))
                    {
                        childrenSet.add(objectToSync);
                    }

                    synchronizedObjects.add(parent);
                }
            }
            else if (objectType.equals(ObjectType.HIGH_ORDER_OBJECT))
            {
                for (String childrenName : objectMetadata.getRelationalFields())
                {
                    Class<T> childrenClass = ParsingUtil.toJavaClass(childrenName);

                    Set<DatabaseObject> children = baseService
                            .findById(objectEntry.getKey())
                            .getChildren(childrenClass);

                    objectToSync.setChildren(childrenClass, children);
                }
                synchronizedObjects.add(objectToSync);
            }
        }
        return synchronizedObjects;
    }
}
