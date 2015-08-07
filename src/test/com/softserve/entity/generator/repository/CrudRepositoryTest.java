package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.config.RepositoryMockConfig;
import com.softserve.entity.generator.entity.DatabaseObject;
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

@ContextConfiguration(classes = RepositoryMockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CrudRepositoryTest
{
    @Autowired
    @InjectMocks
    @Qualifier("entityRepositoryImpl")
    private CrudRepository<DatabaseObject> repository;

    @Autowired
    @Qualifier("entityManagerMock")
    private EntityManager entityManager;

    private static final DatabaseObject object = mock(DatabaseObject.class);

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave()
    {
        repository.save(object);

        verify(entityManager).persist(object);
    }

    @Test
    public void testMerge()
    {
        repository.merge(object);

        verify(entityManager).merge(object);
    }

    @Test
    public void testDelete()
    {
        repository.delete(object);

        verify(entityManager).remove(object);
    }

    @Test
    public void testFindById()
    {
        String id = mock(String.class);
        repository.findById(id);

        verify(entityManager).find(object.getClass(), id);
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
