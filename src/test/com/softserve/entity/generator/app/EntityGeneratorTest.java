package com.softserve.entity.generator.app;

import com.softserve.entity.generator.config.MockAppConfig;
import com.softserve.entity.generator.salesforce.Credentials;
import com.softserve.entity.generator.salesforce.ProcedureExecutor;
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

@ContextConfiguration(classes = MockAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityGeneratorTest
{
    @Autowired
    @InjectMocks
    private EntityGenerator entityGenerator;

    @Autowired
    @Qualifier(value = "procedureExecutorMock")
    private ProcedureExecutor procedureExecutor;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateEntitiesTest()
    {
        Credentials credentials = mock(Credentials.class);
        entityGenerator.generateEntities(credentials);

        verify(procedureExecutor).generateAndExecute(credentials);
    }
}
