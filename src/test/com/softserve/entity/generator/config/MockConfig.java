package com.softserve.entity.generator.config;

import com.softserve.entity.generator.repository.impl.BaseRepositoryImpl;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.EntityApplier;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = {"com.softserve.entity.generator.service", "com.softserve.entity.generator.repository",
        "com.softserve.entity.generator.salesforce"})
public class MockConfig
{
    @Bean
    public EntityApplier entityApplierMock()
    {
        return mock(EntityApplier.class);
    }

    @Bean
    public EntityService entityServiceMock()
    {
        return mock(EntityService.class);
    }

    @Bean
    public WebServiceUtil webServiceUtil()
    {
        return mock(WebServiceUtil.class);
    }

    @Bean
    public BaseRepositoryImpl baseRepositoryMock()
    {
        return Mockito.mock(BaseRepositoryImpl.class);
    }

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
}
