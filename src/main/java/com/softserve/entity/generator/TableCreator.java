package com.softserve.entity.generator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class
        TableCreator
{
    public static void main(String[] args)
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Creation");
        entityManagerFactory.createEntityManager();
        entityManagerFactory.close();
    }
}
