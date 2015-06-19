package com.softserve.entity.generator.service;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public interface EntityService extends BaseRepository<Entity> {}
