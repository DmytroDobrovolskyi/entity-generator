package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.config.MockRepositoryConfig;
import com.softserve.entity.generator.entity.production.Entity;
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
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = MockRepositoryConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CrudRepositoryTest
{
    @Autowired
    @InjectMocks
    @Qualifier("entityRepositoryImpl")
    private CrudRepository<Entity> repository;

    @Autowired
    @Qualifier("entityManagerMock")
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
        repository.findById(id);

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

        assertThat(repository.findAll(), is(any(List.class)));

        verify(entityManager).createQuery(query, entityClass);
    }
}
