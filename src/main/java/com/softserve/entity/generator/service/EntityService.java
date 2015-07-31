package com.softserve.entity.generator.service;

import com.softserve.entity.generator.entity.Entity;
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

    void applyData();
}
