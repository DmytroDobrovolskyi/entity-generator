package com.softserve.entity.generator.service;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.webservice.util.OperationType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business layer interface that do required business logic on Entity object.
 */
@Service
public interface EntityService extends BaseService<Entity>
{
    /**
     * Deletes entities from database that was in turn deleted form Salesforce side.
     *
     * @param entities new entities list that come form Salesforce side
    */
    void resolveDeleted(List<Entity> entities);

    /**
     * Performs batch operation based on {@literal operationType} argument on newly come entities from Salesforce database.
     * Also makes parent-to-child assignment, for instance:
     * {@code
     *  Entity entity = any();
     *  for (Field field : entity.getFields())
     *  {
     *      field.setEntity(entity); //parent-to-child assignment
     *  }
     * }
     *
     * @param entities new entities list that come form Salesforce side
     */
    void processBatchOperation(List<Entity> entities, OperationType operationType);
}
