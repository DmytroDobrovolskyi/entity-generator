package com.softserve.entity.generator.config;

import com.softserve.entity.generator.repository.impl.BaseRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = {"com.softserve.entity.generator.service", "com.softserve.entity.generator.repository"})
//TODO DISTINCT CLASS
public class MockConfig
{
    private static final EntityManagerFactory entityManagerFactoryMock = mock(EntityManagerFactory.class);

    @PostConstruct
    public void init()
    {

        doReturn(entityManagerMock())
                .when(entityManagerFactoryMock)
                .createEntityManager();
    }

    @Bean
    public BaseRepositoryImpl baseRepositoryMock()
    {
        return mock(BaseRepositoryImpl.class);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        return new LocalContainerEntityManagerFactoryBean()
        {
            @Override
            protected EntityManagerFactory createNativeEntityManagerFactory() throws PersistenceException
            {
                return entityManagerFactoryMock;
            }
        };
    }

    @Bean
    public EntityManager entityManagerMock()
    {
        return mock(EntityManager.class);
    }
}

