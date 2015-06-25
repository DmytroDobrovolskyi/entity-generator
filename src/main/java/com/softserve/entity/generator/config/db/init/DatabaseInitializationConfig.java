package com.softserve.entity.generator.config.db.init;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource(value = "/WEB-INF/database.properties")
@ComponentScan(basePackages = "com.softserve.entity.generator.config.db.init")
public class DatabaseInitializationConfig
{
    private static final Logger logger = Logger.getLogger(DatabaseInitializationConfig.class);

    public static final String DATABASE_NAME = "EntityGenerator";
    public static final String SCHEMA_NAME = "core_schema";
    @Autowired
    private Environment env;

    @Bean
    public Connection jdbcConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(
                    env.getProperty("db.url").split(";")[0],
                    env.getProperty("db.username"),
                    env.getProperty("db.password")
            );
        }
        catch (SQLException ex)
        {
            logger.error("Could not create connection: " + ex);
            throw new AssertionError(ex);
        }
        return connection;
    }
}

