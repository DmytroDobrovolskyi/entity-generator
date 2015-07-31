package com.softserve.entity.generator.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Used for execution batch operations.
 */
@Service
public interface BatchService<T>
{
    /**
     * Performs batch merge operation on {@literal objects} passed in parameter.
     * Also makes parent-to-child assignment, for instance:
     * {@code
     *  Entity entity = any();
     *  for (Field field : entity.getFields())
     *  {
     *      field.setEntity(entity); //parent-to-child assignment
     *  }
     * }
     *
     * @param objects new entities list that come form Salesforce side
     */
    void batchMerge(List<T> objects);

    /**
     * Deletes all objects from database whose ids matches ids in {@literal objectIdList}.
     *
     * @param objectIdList ids of entities that will be deleted
     */
    void batchDelete(List<String> objectIdList);
}
