package com.softserve.entity.generator.entity;

import com.sforce.soap.enterprise.sobject.Partner;
import com.softserve.entity.generator.util.ReflectionUtil;
import javassist.NotFoundException;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * Superclass for all objects that resides in Java-side database.
 */
public abstract class DatabaseObject
{
    private static final Logger logger = Logger.getLogger(DatabaseObject.class);

    public String getId()
    {
        try
        {
            return ReflectionUtil.invokeGetter(this, String.class, "get" + this.getClass().getSimpleName() + "Id");
        }
        catch (NotFoundException ex)
        {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public DatabaseObject getParent(Class<? extends DatabaseObject> parentClass)
    {
        try
        {
            return ReflectionUtil.invokeGetter(this, parentClass, "get" + parentClass.getSimpleName());
        }
        catch (NotFoundException ex)
        {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public void setParent(Class<? extends Partner> parentClass, DatabaseObject parent)
    {
        ReflectionUtil.invokeSetter(this, "set" + parentClass.getSimpleName(), parent);
    }

    @SuppressWarnings("unchecked")
    public Set<DatabaseObject> getChildren(Class<? extends DatabaseObject> childrenClass)
    {
        try
        {
            return ReflectionUtil.invokeGetter(this, Set.class, "get" + childrenClass.getSimpleName() + "s");
        }
        catch (NotFoundException ex)
        {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public void setChildren(Class<? extends DatabaseObject> childrenClass, Set<? extends DatabaseObject> children)
    {
        ReflectionUtil.invokeSetter(this, "set" + childrenClass.getSimpleName() + "s", children);
    }
}
