package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.config.MockSalesforceConfig;
import com.softserve.entity.generator.entity.production.Entity;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.EntityApplier;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = MockSalesforceConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProcedureExecutorTest
{
  /*  @Autowired
    @InjectMocks
    private ProcedureExecutor procedureExecutor;*/

    @Autowired
    @Qualifier(value = "entityServiceMock")
    private EntityService entityService;

    @Autowired
    @Qualifier(value = "entityApplierMock")
    private EntityApplier entityApplier;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void generateAndExecuteTest()
    {
        /*procedureExecutor.generateAndExecute(mock(Credentials.class));*/

        List<Entity> entities = Arrays.asList(mock(Entity.class), mock(Entity.class), mock(Entity.class));

        when(entityService.findAll())
                .thenReturn(entities);

        verify(entityService).findAll();
    }
}
