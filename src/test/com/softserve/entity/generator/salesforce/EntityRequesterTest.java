package com.softserve.entity.generator.salesforce;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EntityRequesterTest
{
    @Test
    public void getAllEntitiesTest()
    {
        EntityRequester entityRequester = mock(EntityRequester.class);
        entityRequester.getAllEntities();
        verify(entityRequester).getAllEntities();
    }

    @Test
    public void getFullInfoTest()
    {
        EntityRequester entityRequester = mock(EntityRequester.class);
        entityRequester.getFullInfo();
        verify(entityRequester).getFullInfo();
    }
}
