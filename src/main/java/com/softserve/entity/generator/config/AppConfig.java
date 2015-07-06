package com.softserve.entity.generator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.softserve.entity.generator")
@PropertySource(value = "/WEB-INF/database.properties")
public class AppConfig
{
    @Autowired
    private Environment env;

    @Bean
    public DriverManagerDataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getProperty("em.packagesToScan"));
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager()
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    public Map<String, String> jpaProperties()
    {
        Map<String, String> jpaProperties = new HashMap<String, String>();
        jpaProperties.put("hibernate.dialect", env.getProperty("hb.dialect"));
        jpaProperties.put("hibernate.show_sql", env.getProperty("hb.showSql"));
        jpaProperties.put("hibernate.format_sql", env.getProperty("hb.formatSql"));
        jpaProperties.put("hibernate.use_sql_comments", env.getProperty("hb.sqlComment"));
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hb.hbm2ddl.auto"));
        jpaProperties.put("hibernate.default_schema", env.getProperty("hb.defaultSchema"));
        jpaProperties.put("hibernate.enable_lazy_load_no_trans", env.getProperty("hb.enableLazyLoadNoTrans"));

        return jpaProperties;
    }
}
