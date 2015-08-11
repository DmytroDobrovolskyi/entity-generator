package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.config.RepositoryMockConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

@ContextConfiguration(classes = RepositoryMockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDataRepositoryTest
{
    @Autowired
    @InjectMocks
    private UserDataRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetByFieldId()
    {
        System.out.println("Hell");
    }
}
