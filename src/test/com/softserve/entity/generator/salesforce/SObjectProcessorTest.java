package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.entity.Entity;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SObjectProcessorTest
{
    @Test
    @Ignore
    public void getAllEntitiesTest()
    {
        SObjectProcessor<Entity> SObjectProcessor = new SObjectProcessor<Entity>(mock(Credentials.class), Entity.class);
        List<Entity> entities = new ArrayList<Entity>();
        when(SObjectProcessor.getAll()).thenReturn(entities);

    }
}
