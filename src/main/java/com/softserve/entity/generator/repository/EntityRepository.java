package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EntityRepository extends BaseRepository<Entity>
{
    Set<Field> resolveDeletedFields(Entity entity);
}
