package com.softserve.entity.generator.config;

import com.softserve.entity.generator.repository.EntityRepository;
import com.softserve.entity.generator.repository.impl.CrudRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.softserve.entity.generator.service")
public class ServiceMockConfig
{
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        return new EntityManagerFactoryBeanMock(entityManagerMock());
    }

    @Bean
    public EntityManager entityManagerMock()
    {
        return mock(EntityManager.class);
    }

    @Bean
    public EntityRepository entityRepositoryMock()
    {
        return mock(EntityRepository.class);
    }

    @Bean
    @Primary
    public CrudRepositoryImpl baseRepositoryMock()
    {
        return mock(CrudRepositoryImpl.class);
    }
}
