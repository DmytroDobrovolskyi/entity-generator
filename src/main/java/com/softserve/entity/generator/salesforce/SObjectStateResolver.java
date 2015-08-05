package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.salesforce.util.ObjectType;
import com.softserve.entity.generator.salesforce.util.ParsingUtil;
import com.softserve.entity.generator.service.BaseService;

import java.util.*;

public class SObjectStateResolver
{
    public static <T extends DatabaseObject> List<DatabaseObject> resolveOnUpdate(Map<String, T> idToObject, Class<T> objectClass)
    {

        System.out.println(idToObject);
        @SuppressWarnings("unchecked")
        BaseService<T> baseService = AppContextCache.getContext(AppConfig.class).getBean(BaseService.class);

        List<DatabaseObject> resolvedObjects = new ArrayList<DatabaseObject>();
        SObjectMetadata objectMetadata = SObjectRegister.getSObjectMetadata(objectClass);
        ObjectType objectType = objectMetadata.getObjectType();

        Map<String, DatabaseObject> idToParentObject  = new HashMap<String, DatabaseObject>();
        for (Map.Entry<String, T> objectEntry : idToObject.entrySet())
        {
            T newStateObject = objectEntry.getValue();

            switch (objectType)
            {
                case HIGH_ORDER_OBJECT:
                    for (String childrenName : objectMetadata.getRelationalFields())
                    {
                        Class<T> childrenClass = ParsingUtil.toJavaClass(childrenName);

                        Set<DatabaseObject> children = baseService
                                .findById(objectEntry.getKey())
                                .getChildren(childrenClass);

                        newStateObject.setChildren(childrenClass, children);
                    }
                    resolvedObjects.add(newStateObject);
                    break;
                case SUBORDER_OBJECT:
                    for (String parentName : objectMetadata.getRelationalFields())
                    {
                        String parentId = newStateObject.getParent(ParsingUtil.<T>toJavaClass(parentName)).getId();
                        DatabaseObject parent = idToParentObject.get(parentId);
                        if (parent == null)
                        {
                            parent = baseService.findById(parentId);
                            idToParentObject.put(parentId, parent);
                        }
                        Set<DatabaseObject> childrenSet = parent.getChildren(objectClass);

                        childrenSet.remove(newStateObject);
                        childrenSet.add(newStateObject);
                    }
                    resolvedObjects.addAll(idToParentObject.values());
            }
        }
        System.out.println(resolvedObjects);
        return resolvedObjects;
    }
}
