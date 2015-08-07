package com.softserve.entity.generator.service;

import com.softserve.entity.generator.config.ServiceMockConfig;
import com.softserve.entity.generator.entity.production.Entity;
import com.softserve.entity.generator.repository.EntityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = ServiceMockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityServiceTest
{
    @Autowired
    @Qualifier(value = "entityRepositoryMock")
    private EntityRepository entityRepository;

    @Autowired
    @InjectMocks
    EntityService entityService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void resolveDeletedTest()
    {
        List<Entity> entityList = new ArrayList<Entity>();
        Entity entity = mock(Entity.class);
        entityList.add(entity);

        entityService.resolveDeleted(entityList);

        when(entityService.findAll())
                .thenReturn(entityList);

        verify(entityRepository).findAll();
    }
}
