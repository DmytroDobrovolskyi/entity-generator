package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.entity.production.Entity;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends CrudRepository<Entity>
{
    Entity getByFieldId(String fieldId);
}
