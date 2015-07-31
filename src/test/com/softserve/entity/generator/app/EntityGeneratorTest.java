package com.softserve.entity.generator.app;

import com.softserve.entity.generator.config.AppMockConfig;
import com.softserve.entity.generator.salesforce.SalesforceCredentials;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = AppMockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityGeneratorTest
{
    @Autowired
    @InjectMocks
    private EntityGenerator entityGenerator;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void generateEntitiesTest()
    {
        SalesforceCredentials salesforceCredentials = mock(SalesforceCredentials.class);
    }
}
