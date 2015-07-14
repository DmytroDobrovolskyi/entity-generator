package com.softserve.entity.generator.service;

import com.softserve.entity.generator.entity.Entity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EntityService extends BaseService<Entity>
{
    void saveAndResolveDeleted(List<Entity> entities);
}
