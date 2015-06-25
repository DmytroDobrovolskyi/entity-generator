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
import static com.softserve.entity.generator.config.database.init.util.DatabaseUtil.isExistDatabase;

@Component
public class DatabaseCleaner
{
    private static final Logger logger = Logger.getLogger(DatabaseCleaner.class);

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
        DatabaseCleaner dbCleaner = context.getBean(DatabaseCleaner.class);
        dbCleaner.cleanDatabase();
    }

    public void cleanDatabase()
    {
        if (isExistDatabase(DATABASE_NAME))
        {
            jdbcTemplate.execute(
                            "USE master;" +
                            "ALTER DATABASE [" + DATABASE_NAME + "] SET SINGLE_USER WITH ROLLBACK IMMEDIATE; " +
                            "DROP DATABASE [" + DATABASE_NAME + "]"
            );
            logger.info("Database " + DATABASE_NAME + " successfully deleted");
        } else
        {
            logger.info("Database " + DATABASE_NAME + " does not exist");
        }
    }
}
