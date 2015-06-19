package com.softserve.entity.generator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DatabaseInitializer
{
    private static final Logger logger = Logger.getLogger(Main.class);

    @PersistenceContext(unitName = "init-db")
    @Qualifier(value = "databaseInitializerEntityManagerFactory")
    private EntityManager entityManager;

    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext
                ("spring/ApplicationContext.xml");
        DatabaseInitializer initializer = context.getBean(DatabaseInitializer.class);
        logger.info("Initialized database");
        initializer.closeConnection();
    }

    public void closeConnection()
    {
        entityManager.close();
    }
}
