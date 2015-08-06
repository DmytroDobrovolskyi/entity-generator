package com.softserve.entity.generator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"/META-INF/operations-db.properties", "/META-INF/jpa.properties"})
public class OperationsDatabaseConfig
{
    @Autowired
    private Environment env;

    @Bean
    public SharedEntityManagerBean operationsEntityManager()
    {
        SharedEntityManagerBean entityManagerBean = new SharedEntityManagerBean();
        entityManagerBean.setEntityManagerFactory(operationsEntityManagerFactory().getObject());
        return entityManagerBean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean operationsEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(operationsDataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getProperty("op.entities"));
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager operationsTransactionManager()
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(operationsEntityManagerFactory().getObject());
        transactionManager.setDataSource(operationsDataSource());
        return transactionManager;
    }

    @Bean
    public DriverManagerDataSource operationsDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("op.db.driver"));
        dataSource.setUrl(env.getProperty("op.db.url"));
        dataSource.setUsername(env.getProperty("op.db.username"));
        dataSource.setPassword(env.getProperty("op.db.password"));
        return dataSource;
    }

    private Map<String, String> jpaProperties()
    {
        Map<String, String> jpaProperties = new HashMap<String, String>();
        jpaProperties.put("hibernate.dialect", env.getProperty("hb.dialect"));
        jpaProperties.put("hibernate.show_sql", env.getProperty("hb.showSql"));
        jpaProperties.put("hibernate.format_sql", env.getProperty("hb.formatSql"));
        jpaProperties.put("hibernate.use_sql_comments", env.getProperty("hb.sqlComment"));
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hb.hbm2ddl.auto"));
        jpaProperties.put("hibernate.default_schema", env.getProperty("hb.defaultSchema"));

        return jpaProperties;
    }
}
