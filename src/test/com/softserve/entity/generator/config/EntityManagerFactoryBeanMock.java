package com.softserve.entity.generator.config;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class EntityManagerFactoryBeanMock extends LocalContainerEntityManagerFactoryBean
{
    private static final EntityManagerFactory entityManagerFactoryMock = mock(EntityManagerFactory.class);

    public EntityManagerFactoryBeanMock(EntityManager entityManagerMock)
    {
        doReturn(entityManagerMock)
                .when(entityManagerFactoryMock)
                .createEntityManager();
    }

    @Override
    protected EntityManagerFactory createNativeEntityManagerFactory() throws PersistenceException
    {
        return entityManagerFactoryMock;
    }
}
