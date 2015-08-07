package com.softserve.entity.generator.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource(value = "/META-INF/production-db.properties")
@ComponentScan(basePackages = "com.softserve.entity.generator.config.database.init")
public class ProductionDatabaseInitConfig
{
    private static final Logger logger = Logger.getLogger(ProductionDatabaseInitConfig.class);

    public static final String DATABASE_NAME = "entity_generator";
    public static final String DEFAULT_SCHEMA = "core_schema";
    public static final String GENERATED_TABLES_SCHEMA = "mdd";

    @Autowired
    private Environment env;

    @Bean
    public DriverManagerDataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("prod.db.driver"));
        dataSource.setUrl(env.getProperty("prod.db.url").split(";")[0]);    //requires pure database server url, not with specific database
        dataSource.setUsername(env.getProperty("prod.db.username"));
        dataSource.setPassword(env.getProperty("prod.db.password"));
        return dataSource;
    }
}
