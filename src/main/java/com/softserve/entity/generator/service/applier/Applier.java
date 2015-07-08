package com.softserve.entity.generator.service.applier;

import org.springframework.stereotype.Service;

@Service
public interface Applier<T>
{
    boolean apply(T entity);
}
