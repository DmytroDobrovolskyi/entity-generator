package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.config.RepositoryMockConfig;
import com.softserve.entity.generator.entity.Entity;
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
import javax.persistence.TypedQuery;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = RepositoryMockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityRepositoryTest
{
    @Autowired
    @InjectMocks
    @Qualifier("entityRepositoryImpl")
    private EntityRepository repository;

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
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        repository.save(entity);

        verify(entityManager).persist(entity);
    }

    @Test
    public void testMerge()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        repository.merge(entity);

        verify(entityManager).merge(entity);
    }

    @Test
    public void testDelete()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        repository.delete(entity);

        verify(entityManager).remove(entity);
    }

    @Test
    public void testFindById()
    {
        String id = "testId";


        verify(entityManager).find(Entity.class, id);
    }

    @Test
    public void testFindAll()
    {
        Class<Entity> entityClass = Entity.class;
        String query = "FROM " + entityClass.getSimpleName();

        doReturn(mock(TypedQuery.class))
                .when(entityManager)
                .createQuery(query, entityClass);



        verify(entityManager).createQuery(query, entityClass);
    }
}
