package com.softserve.entity.generator.app;

import com.softserve.entity.generator.config.MockAppConfig;
import com.softserve.entity.generator.entity.production.Entity;
import com.softserve.entity.generator.service.EntityService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = MockAppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntitySaverTest
{
    @Autowired
    @InjectMocks
    private EntitySaver entitySaver;

    @Autowired
    @Qualifier(value = "entityServiceMock")
    private EntityService entityService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void saveEntitiesTest()
    {
        Entity entity = mock(Entity.class);
        List<Entity>entities = new ArrayList<Entity>(Arrays.asList(entity,entity));
//        entitySaver.saveEntities(entities);

        verify(entityService).resolveDeleted(entities);

        verify(entityService, times(2)).merge(entity);
    }
}
