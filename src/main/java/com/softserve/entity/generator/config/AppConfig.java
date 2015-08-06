package com.softserve.entity.generator.config;

import com.softserve.entity.generator.config.util.Exclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
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
@Import(value = OperationsDatabaseConfig.class)
@EnableTransactionManagement
@ComponentScan(basePackages = "com.softserve.entity.generator", excludeFilters = {@ComponentScan.Filter(
        type = FilterType.ANNOTATION, value = {Configuration.class, Exclude.class})})
@PropertySource(value = {"/META-INF/production-db.properties", "/META-INF/jpa.properties"})
public class AppConfig
{
    @Autowired
    private Environment env;

    @Bean
    @Primary
    public SharedEntityManagerBean productionEntityManager()
    {
        SharedEntityManagerBean entityManagerBean = new SharedEntityManagerBean();
        entityManagerBean.setEntityManagerFactory(productionEntityManagerFactory().getObject());
        return entityManagerBean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean productionEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(productionDataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getProperty("prod.entities"));
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager productionTransactionManager()
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(productionEntityManagerFactory().getObject());
        transactionManager.setDataSource(productionDataSource());
        return transactionManager;
    }

    @Bean
    public DriverManagerDataSource productionDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("prod.db.driver"));
        dataSource.setUrl(env.getProperty("prod.db.url"));
        dataSource.setUsername(env.getProperty("prod.db.username"));
        dataSource.setPassword(env.getProperty("prod.db.password"));
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
        jpaProperties.put("hibernate.enable_lazy_load_no_trans", env.getProperty("hb.enableLazyLoadNoTrans"));
        jpaProperties.put("hibernate.default_schema", env.getProperty("hb.defaultSchema"));

        return jpaProperties;
    }
}
