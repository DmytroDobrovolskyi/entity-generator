package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.salesforce.util.ObjectType;
import com.softserve.entity.generator.salesforce.util.ParsingUtil;
import com.softserve.entity.generator.service.BaseService;

import java.util.*;

public class ObjectStateResolver
{
    public static <T extends DatabaseObject> List<DatabaseObject> resolveOnUpdate(Map<String, T> idToObject, Class<T> objectClass)
    {

        @SuppressWarnings("unchecked")
        BaseService<T> baseService = AppContextCache.getContext(AppConfig.class).getBean(BaseService.class);

        List<DatabaseObject> resolvedObjects = new ArrayList<DatabaseObject>();
        SObjectMetadata objectMetadata = SObjectRegister.getSObjectMetadata(objectClass);
        ObjectType objectType = objectMetadata.getObjectType();

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
                        DatabaseObject parent = newStateObject.getParent(ParsingUtil.<T>toJavaClass(parentName));

                        Set<DatabaseObject> childrenSet = parent.getChildren(objectClass);

                        if (childrenSet.remove(newStateObject))
                        {
                            childrenSet.add(newStateObject);
                        }
                        resolvedObjects.add(parent);
                    }
            }
        }
        return resolvedObjects;
    }
}
