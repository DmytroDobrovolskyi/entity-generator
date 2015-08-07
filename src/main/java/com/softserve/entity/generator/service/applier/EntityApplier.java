package com.softserve.entity.generator.service.applier;

import com.softserve.entity.generator.entity.production.Entity;

public interface EntityApplier extends Applier<Entity>
{
    void resolveNonExisting();
}
