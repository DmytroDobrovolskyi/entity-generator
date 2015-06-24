package com.softserve.entity.generator.config.db.init;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.softserve.entity.generator.config.db.init.DatabaseInitConfig.DATABASE_NAME;

@Component
public class DatabaseInitializer
{
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class);

    @Autowired
    private Connection connection;

    public static void main(String[] args)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseInitConfig.class);
        DatabaseInitializer dbInitializer = context.getBean(DatabaseInitializer.class);
        dbInitializer.initDatabase();
    }

    public void initDatabase()
    {
        try
        {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE DATABASE " + DATABASE_NAME);

            statement.executeUpdate("USE EntityGenerator");

            statement.executeUpdate("CREATE SCHEMA core_schema");
            logger.info("Database " + DATABASE_NAME + " created");
        }
        catch (SQLException ex)
        {
            logger.error("Could not create database: " + ex);
            throw new AssertionError(ex);
        }
    }
}
