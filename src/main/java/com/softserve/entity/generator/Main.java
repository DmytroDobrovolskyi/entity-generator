package com.softserve.entity.generator;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class Main
{
    private static final Logger logger = Logger.getLogger(Main.class);

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext
                ("spring/ApplicationContext.xml");
        Main main = context.getBean(Main.class);
        main.test();
    }

    @Transactional
    public void test()
    {
        Query query = entityManager.createNativeQuery("SELECT name FROM ENTITY");
        logger.info(query.getResultList());
        entityManager.close();
    }
}
