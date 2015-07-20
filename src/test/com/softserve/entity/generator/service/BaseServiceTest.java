package com.softserve.entity.generator.service;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.BaseRepository;
import com.softserve.entity.generator.service.BaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseServiceTest
{
    @Autowired
    @InjectMocks
    private BaseService<Entity> service;

    @Autowired
    @Qualifier(value = "baseRepositoryMock")
    private BaseRepository<Entity> repository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        service.save(entity);

        verify(repository).save(entity);
    }

    @Test
    public void testMerge()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        service.merge(entity);

        verify(repository).merge(entity);
    }

    @Test
    public void testDelete()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        service.delete(entity);

        verify(repository).delete(entity);
    }

    @Test
    public void testFindById()
    {
        String id = "testId";
        service.findById(id);

        verify(repository).findById(id);
    }

    @Test
    public void testFindAll()
    {
        assertThat(repository.findAll(), is(any(List.class)));

        verify(repository).findAll();
    }
}
