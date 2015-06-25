package com.softserve.entity.generator.config.db.init;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.softserve.entity.generator.config.db.init.DatabaseInitializationConfig.DATABASE_NAME;

@Component
public class DatabaseCleaner
{
    private static final Logger logger = Logger.getLogger(DatabaseCleaner.class);

    @Autowired
    private Connection connection;

    public static void main(String[] args)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseInitializationConfig.class);
        DatabaseCleaner dbCleaner = context.getBean(DatabaseCleaner.class);
        dbCleaner.cleanDatabase();
    }

    public void cleanDatabase()
    {
        try
        {
            Statement statement = connection.createStatement();

            statement.executeUpdate("USE master;" +
                    "ALTER DATABASE [" + DATABASE_NAME + "] SET SINGLE_USER WITH ROLLBACK IMMEDIATE; " +
                    "DROP DATABASE [" + DATABASE_NAME + "]");

            logger.info("Database " + DATABASE_NAME + " dropped");
        }
        catch (SQLException ex)
        {
            logger.error("Could not drop database: " + ex);
            throw new AssertionError(ex);
        }
    }
}
