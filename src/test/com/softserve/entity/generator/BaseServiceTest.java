package com.softserve.entity.generator;

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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseServiceTest
{
    @Autowired
    @Qualifier("baseServiceImpl")
    @InjectMocks
    private BaseService<Entity> service;

    @Qualifier("baseRepositoryMock")
    @Autowired
    private BaseRepository<Entity> repository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave()
    {
        Entity entity = new Entity("testId", "testName");
        service.save(entity);

        verify(repository).save(entity);
    }

    @Test
    public void testMerge()
    {
        Entity entity = new Entity("testId", "testName");
        service.merge(entity);

        verify(repository).merge(entity);

    }

    @Test
    public void testDelete()
    {
        Entity entity = new Entity("testId", "testName");
        service.delete(entity);

        verify(repository).delete(entity);
    }

    @Test
    public void testFindById()
    {
        String id = "testId";
        Entity entity = new Entity(id, "testName");
        when(service.findById(id))
                .thenReturn(entity);

        assertThat(service.findById(id), equalTo(entity));

        verify(repository).findById(id);
    }

    @Test
    public void testFindAll()
    {
        List<Entity> resultList = new ArrayList<Entity>();
        when(service.findAll())
                .thenReturn(resultList);

        assertThat(service.findAll(), equalTo(resultList));

        verify(repository).findAll();
    }
}
