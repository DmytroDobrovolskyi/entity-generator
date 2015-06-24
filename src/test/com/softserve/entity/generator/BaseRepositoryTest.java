package com.softserve.entity.generator;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.BaseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseRepositoryTest
{
    @Autowired
    @Qualifier("baseRepositoryImpl")
    @InjectMocks
    private BaseRepository<Entity> repository;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave()
    {
        Entity entity = new Entity("testId", "testName");
        repository.save(entity);

        verify(entityManager).persist(entity);
    }

}
