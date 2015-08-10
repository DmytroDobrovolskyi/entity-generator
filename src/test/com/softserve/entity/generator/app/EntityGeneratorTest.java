package com.softserve.entity.generator.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@ContextConfiguration(classes = MockAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityGeneratorTest
{
    @Autowired
    @InjectMocks
    private EntityGenerator entityGenerator;

  /*  @Autowired
    @Qualifier(value = "procedureExecutorMock")
    private ProcedureExecutor procedureExecutor;
*/
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateEntitiesTest()
    {
      /*  Credentials credentials = mock(Credentials.class);
        entityGenerator.generateEntities(credentials);

        verify(procedureExecutor).generateAndExecute(credentials);*/
    }
}
