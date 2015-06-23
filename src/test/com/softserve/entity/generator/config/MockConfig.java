package com.softserve.entity.generator.config;

import com.softserve.entity.generator.repository.impl.BaseRepositoryImpl;
import com.softserve.entity.generator.service.impl.BaseServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class MockConfig
{
    @Bean
    public EntityManagerFactory entityManagerFactory()
    {
        return Mockito.mock(EntityManagerFactory.class);
    }

    @Bean
    public BaseRepositoryImpl baseRepositoryImpl()
    {
        return Mockito.mock(BaseRepositoryImpl.class);
    }

    @Bean
    public BaseServiceImpl baseServiceImpl()
    {
        return Mockito.mock(BaseServiceImpl.class);
    }
}
