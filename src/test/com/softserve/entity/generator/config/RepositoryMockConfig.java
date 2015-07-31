package com.softserve.entity.generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.softserve.entity.generator.repository")
public class RepositoryMockConfig
{
    @Bean
    public EntityManager entityManagerMock()
    {
        return mock(EntityManager.class);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        return new EntityManagerFactoryBeanMock(entityManagerMock());
    }
}
