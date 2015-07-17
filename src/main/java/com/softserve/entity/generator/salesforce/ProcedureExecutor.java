package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.EntityApplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcedureExecutor
{
    private static final String RESET_IS_PROCESSING_NEEDED = "EntityUtil.resetIsProcessingNeeded();";

    @Autowired
    private EntityApplier entityApplier;

    @Autowired
    private EntityService entityService;

    public void generateAndExecute(SalesforceAuthenticator salesforceAuthenticator)
    {
        entityApplier.applyAll(
                entityService.findAll()
        );
        ApexExecutor.executeApex(salesforceAuthenticator.getLoginResult(), RESET_IS_PROCESSING_NEEDED);
    }
}
