package com.softserve.entity.generator.config;

import com.softserve.entity.generator.repository.impl.BaseRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.softserve.entity.generator.service")
public class MockConfig
{
    @Bean
    public BaseRepositoryImpl baseRepositoryImpl()
    {
        return mock(BaseRepositoryImpl.class);
    }

    @Bean
    public EntityManagerFactory entityManagerFactoryMock()
    {
        return mock(EntityManagerFactory.class);
    }
}
