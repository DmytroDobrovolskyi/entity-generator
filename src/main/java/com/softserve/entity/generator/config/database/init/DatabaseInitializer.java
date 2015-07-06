package com.softserve.entity.generator.config.database.init;

import com.softserve.entity.generator.config.DatabaseInitializationConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import static com.softserve.entity.generator.config.DatabaseInitializationConfig.DATABASE_NAME;
import static com.softserve.entity.generator.config.DatabaseInitializationConfig.SCHEMA_NAME;

@Component
public class DatabaseInitializer
{
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class);

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init()
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void main(String[] args)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseInitializationConfig.class);
        DatabaseInitializer dbInitializer = context.getBean(DatabaseInitializer.class);
        dbInitializer.initDatabase();
    }

    public void initDatabase()
    {
        jdbcTemplate.execute("IF ( EXISTS(" +
                "SELECT name " +
                "FROM master.dbo.sysdatabases " +
                "WHERE name='" + DATABASE_NAME + "')) " +
                "DROP DATABASE " + DATABASE_NAME + "; " +
                "CREATE DATABASE " + DATABASE_NAME + "; " +
                "USE " + DATABASE_NAME + "; " +
                "EXEC ('CREATE SCHEMA " + SCHEMA_NAME + " ;');"
        );
        logger.info("Database was successfully initialized");
    }
}
