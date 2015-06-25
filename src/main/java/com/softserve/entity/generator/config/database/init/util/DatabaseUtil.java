package com.softserve.entity.generator.config.database.init.util;

import com.softserve.entity.generator.config.DatabaseInitializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

import static com.softserve.entity.generator.config.DatabaseInitializationConfig.DATABASE_NAME;

@Component
public class DatabaseUtil
{
    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init()
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static boolean isExistDatabase(String databaseName)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseInitializationConfig.class);
        DatabaseUtil util = context.getBean(DatabaseUtil.class);
        return util.requestDatabasesNames().contains(databaseName);
    }

    private List<String> requestDatabasesNames()
    {
        return jdbcTemplate.queryForList(
                        "SELECT name " +
                        "FROM master.dbo.sysdatabases " +
                        "WHERE name='" + DATABASE_NAME + "'", String.class);
    }
}
