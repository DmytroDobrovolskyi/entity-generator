package com.softserve.entity.generator.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource(value = "/WEB-INF/database.properties")
@ComponentScan(basePackages = "com.softserve.entity.generator.config.database.init")
public class DatabaseInitializationConfig
{
    private static final Logger logger = Logger.getLogger(DatabaseInitializationConfig.class);

    public static final String DATABASE_NAME = "entity_generator";
    public static final String SCHEMA_NAME = "core_schema";

    @Autowired
    private Environment env;

    @Bean
    public DriverManagerDataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url").split(";")[0]);
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }
}
