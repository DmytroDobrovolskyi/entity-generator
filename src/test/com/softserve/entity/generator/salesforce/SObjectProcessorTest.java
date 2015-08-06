package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.entity.production.Entity;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;


public class SObjectProcessorTest
{
    @Test
    @Ignore
    public void getAllEntitiesTest()
    {
        SObjectProcessor<Entity> sObjectProcessor = SObjectProcessor.getInstance(anyString(), Entity.class);
        List<Entity> entities = new ArrayList<Entity>();
//        when(sObjectProcessor.getAll()).thenReturn(entities);

    }
}
