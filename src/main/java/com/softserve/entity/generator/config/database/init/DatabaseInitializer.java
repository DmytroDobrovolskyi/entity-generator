package com.softserve.entity.generator.config.database.init;

import com.softserve.entity.generator.config.ProductionDatabaseInitConfig;
import com.softserve.entity.generator.config.util.Exclude;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import static com.softserve.entity.generator.config.ProductionDatabaseInitConfig.*;

@Component
@Exclude
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
        ApplicationContext context = new AnnotationConfigApplicationContext(ProductionDatabaseInitConfig.class);
        DatabaseInitializer dbInitializer = context.getBean(DatabaseInitializer.class);
        dbInitializer.initDatabase();
        dbInitializer.initSchemas();
        logger.info("Database was successfully initialized");
    }

    public void initDatabase()
    {
        jdbcTemplate.execute(
                "IF EXISTS" +
                "(" +
                    "SELECT name " +
                    "FROM sys.databases " +
                    "WHERE name='" + DATABASE_NAME + "'" +
                ") " +
                "DROP DATABASE " + DATABASE_NAME + " " +

                "EXEC('CREATE DATABASE " + DATABASE_NAME + "')"
        );
    }

    public void initSchemas()
    {
        jdbcTemplate.execute(
                "USE " + DATABASE_NAME + " " +
                "EXEC('CREATE SCHEMA " + DEFAULT_SCHEMA + " ') " +
                "EXEC('CREATE SCHEMA " + GENERATED_TABLES_SCHEMA + "')"
        );
    }
}
