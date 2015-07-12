package com.softserve.entity.generator.service;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface EntityService extends BaseService<Entity>
{
    void mergeAndResolveDeletedFields(Entity entity);
}
