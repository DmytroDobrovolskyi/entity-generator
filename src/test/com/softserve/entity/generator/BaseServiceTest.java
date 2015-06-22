package com.softserve.entity.generator;

import com.softserve.entity.generator.config.JPAConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.BaseRepository;
import com.softserve.entity.generator.service.BaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BaseServiceTest
{
    @Mock
    private BaseRepository<Entity> baseRepository;

    @Autowired
    @InjectMocks
    private BaseService<Entity> baseService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave()
    {
        Entity entity = new Entity(randomUUID().toString(), "Any");
        baseService.save(entity);
        verify(baseRepository).save(entity);
    }

    @Test
    public void testMerge()
    {
        Entity entity = new Entity(randomUUID().toString(), "Any");
        baseService.merge(entity);
        verify(baseRepository).merge(entity);
    }

    @Test
    public void testDelete()
    {
        Entity entity = new Entity(randomUUID().toString(), "Any");
        baseService.delete(entity);
        verify(baseRepository).delete(entity);
    }

    @Test
    public void testFindById()
    {
        String id = randomUUID().toString();
        Entity entity = new Entity(id, "Any");
        when(baseRepository.findById(id))
                .thenReturn(entity);

        assertThat(baseRepository.findById(id), equalTo(entity));

    }

    @Test
    public void testFindAll()
    {
        List<Entity> resultList = new ArrayList<Entity>();
        when(baseRepository.findAll())
                .thenReturn(resultList);

        assertThat(baseRepository.findAll(), equalTo(resultList));
    }
}
