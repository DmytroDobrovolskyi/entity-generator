package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.entity.DatabaseObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseSearchRepository<T extends DatabaseObject> extends BaseRepository<T>
{
    T findById(String id);

    List<T> findAll();
}
