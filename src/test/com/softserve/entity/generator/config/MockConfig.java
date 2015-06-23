package com.softserve.entity.generator.config;

import com.softserve.entity.generator.repository.impl.BaseRepositoryImpl;
import com.softserve.entity.generator.service.applier.impl.EntityApplier;
import com.softserve.entity.generator.service.impl.BaseServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static org.mockito.Mockito.mock;

@Configuration
public class MockConfig
{
    @Bean
    public EntityManagerFactory entityManagerFactory()
    {
        return mock(EntityManagerFactory.class);
    }

    @Bean
    public BaseRepositoryImpl baseRepositoryImpl()
    {
        return mock(BaseRepositoryImpl.class);
    }

    @Bean
    public BaseServiceImpl baseServiceImpl()
    {
        return mock(BaseServiceImpl.class);
    }

    @Bean
    public EntityApplier entityApplier()
    {
        return mock(EntityApplier.class);
    }
}
