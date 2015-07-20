package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.EntityApplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProcedureExecutorTest
{

    @Autowired
    @Qualifier(value = "entityServiceMock")
    private EntityService entityService;

    @Autowired
    @Qualifier(value = "entityApplierMock")
    private EntityApplier entityApplier;

    @Autowired
    private SalesforceAuthenticator salesforceAuthenticator;

    @Autowired
    @InjectMocks
    private ProcedureExecutor procedureExecutor;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateAndExecuteTest()
    {
        procedureExecutor.generateAndExecute(salesforceAuthenticator);

        verify(entityService).findAll();
    }
}
