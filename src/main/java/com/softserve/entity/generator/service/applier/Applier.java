package com.softserve.entity.generator.service.applier;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Applier<T>
{
    void apply(T entity);

    void applyAll(List<T> entities);
}
