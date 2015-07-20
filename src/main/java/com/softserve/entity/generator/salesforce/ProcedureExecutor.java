package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.EntityApplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcedureExecutor
{
    private static final String RESET_IS_PROCESSING_NEEDED = "EntityUtil.resetIsProcessingNeeded();";

    @Autowired
    private EntityApplier entityApplier;

    @Autowired
    private ApexExecutor apexExecutor;

    @Autowired
    private EntityService entityService;

    public void generateAndExecute(SalesforceAuthenticator salesforceAuthenticator)
    {
        List<Entity> entitiesToProcess = entityService.findAll();

        entityApplier.applyAll(entitiesToProcess);

        for (Entity entity : entitiesToProcess)
        {
            entity.setIsProcessingNeeded(false);
            entityService.merge(entity);
        }
        apexExecutor.executeApex(salesforceAuthenticator.getLoginResult(), RESET_IS_PROCESSING_NEEDED);
    }
}
