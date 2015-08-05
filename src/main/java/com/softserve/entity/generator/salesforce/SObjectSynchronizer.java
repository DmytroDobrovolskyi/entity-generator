package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.salesforce.util.ObjectType;
import com.softserve.entity.generator.salesforce.util.ParsingUtil;
import com.softserve.entity.generator.service.BaseService;
import com.softserve.entity.generator.service.BatchService;
import com.softserve.entity.generator.webservice.OperationType;

import java.util.*;

public class SObjectSynchronizer
{
    public static <T extends DatabaseObject> void sync(List<String> idList, OperationType operationType, Class<T> objectClass, String sessionId)
    {
        @SuppressWarnings("unchecked")
        BatchService<T> batchService = AppContextCache.getContext(AppConfig.class).getBean(BatchService.class);

        SObjectMetadata objectMetadata = SObjectRegister.getSObjectMetadata(objectClass);
        ObjectType objectType = objectMetadata.getObjectType();
        if (operationType.equals(OperationType.DELETE_OPERATION))
        {
           batchService.batchDelete(idList, objectClass);
        }
        @SuppressWarnings("unchecked")
        BaseService<T> baseService = AppContextCache.getContext(AppConfig.class).getBean(BaseService.class);

        String sObjectName = objectClass.getSimpleName() + "Id__c";
        FetchType fetchType = objectType.equals(ObjectType.HIGH_ORDER_OBJECT) ? FetchType.LAZY : FetchType.EAGER;

        List<T> sObjects = SObjectProcessor.getInstance(sessionId, objectClass).getAll(sObjectName, idList, fetchType);

        Map<String, T> idToObject = getIdToObject(idList, sObjects);
        Map<String, DatabaseObject> idToParentObject = new HashMap<String, DatabaseObject>(); //in SUBORDER_OBJECT case

        List<DatabaseObject> mergeList = new ArrayList<DatabaseObject>(); //objects to perform merge operations

        for (Map.Entry<String, T> idToObjectEntry : idToObject.entrySet())
        {
            T objectToMerge = idToObjectEntry.getValue();
            switch (objectType)
            {
                case HIGH_ORDER_OBJECT:
                    if (operationType.equals(OperationType.UPDATE_OPERATION))
                    {
                        for (String childrenName : objectMetadata.getRelationalFields())
                        {
                            Class<T> childrenClass = ParsingUtil.toJavaClass(childrenName);
                            Set<DatabaseObject> children = baseService
                                    .findById(idToObjectEntry.getKey())
                                    .getChildren(childrenClass);
                            objectToMerge.setChildren(childrenClass, children);
                        }
                    }
                    mergeList.add(objectToMerge);
                case SUBORDER_OBJECT:
                    for (String parentName : objectMetadata.getRelationalFields())
                    {
                        String parentId = objectToMerge.getParent(ParsingUtil.<T>toJavaClass(parentName)).getId();
                        DatabaseObject parent = idToParentObject.get(parentId);
                        if (parent == null)
                        {
                            parent = baseService.findById(parentId);
                            idToParentObject.put(parentId, parent);
                        }
                        Set<DatabaseObject> childrenSet = parent.getChildren(objectClass);
                        childrenSet.remove(objectToMerge);
                        if (operationType.equals(OperationType.DELETE_OPERATION))
                        {
                            childrenSet.add(objectToMerge);
                        }
                    }
                    mergeList.addAll(idToParentObject.values());
            }
        }
    }

    private static <T extends DatabaseObject> Map<String, T> getIdToObject(List<String> ids, List<T> objects)
    {
        int objectsQuantity = objects.size();
        int idsQuantity = ids.size();
        if (objectsQuantity != idsQuantity)
        {
            throw new AssertionError("Could not convert to map: ids size=" + idsQuantity + " objects size=" + objectsQuantity);
        }

        Map<String, T> resultMap = new HashMap<String, T>();
        for (int i = 0; i < objectsQuantity; i++)
        {
            resultMap.put(ids.get(i), objects.get(i));
        }
        return resultMap;
    }
}
