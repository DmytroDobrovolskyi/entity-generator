package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.entity.Entity;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SObjectRequesterTest
{
    @Test
    @Ignore
    public void getAllEntitiesTest()
    {
        SObjectRequester<Entity> SObjectRequester = new SObjectRequester<Entity>(mock(Credentials.class), Entity.class);
        List<Entity> entities = new ArrayList<Entity>();
        when(SObjectRequester.getAll()).thenReturn(entities);

    }
}
