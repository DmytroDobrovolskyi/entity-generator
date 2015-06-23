package com.softserve.entity.generator;

import com.softserve.entity.generator.config.JPAConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.EntityRepository;
import com.softserve.entity.generator.service.BaseService;
import com.softserve.entity.generator.service.EntityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = JPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityServiceTest
{
    @Mock
    private EntityRepository repository;

    @Autowired
    @InjectMocks
    private EntityService service;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave()
    {
        Entity entity = new Entity(randomUUID().toString(), "Any");
        repository.save(entity);
        verify(repository).save(entity);
    }

    @Test
    public void testMerge()
    {
        Entity entity = new Entity(randomUUID().toString(), "Any");
        repository.merge(entity);
        verify(repository).merge(entity);
    }

    @Test
    public void testDelete()
    {
        Entity entity = new Entity(randomUUID().toString(), "Any");
        repository.delete(entity);
        verify(repository).delete(entity);
    }

    @Test
    public void testFindById()
    {
        String id = randomUUID().toString();
        Entity entity = new Entity(id, "Any");
        when(repository.findById(id))
                .thenReturn(entity);

        assertThat(repository.findById(id), equalTo(entity));

        verify(repository).findById(id);
    }

    @Test
    public void testFindAll()
    {
        List<Entity> resultList = new ArrayList<Entity>();
        when(repository.findAll())
                .thenReturn(resultList);

        assertThat(repository.findAll(), equalTo(resultList));

        verify(repository).findAll();
    }
}
