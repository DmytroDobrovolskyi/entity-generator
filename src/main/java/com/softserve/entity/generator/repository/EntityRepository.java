package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.entity.Entity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends BaseRepository<Entity> { }
