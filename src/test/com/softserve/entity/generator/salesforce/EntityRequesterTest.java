package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.entity.Entity;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class EntityRequesterTest
{
    @Test
    @Ignore
    public void getAllEntitiesTest()
    {
        EntityRequester entityRequester = new EntityRequester(mock(Credentials.class));
        List<Entity> entities = new ArrayList<Entity>();
        when(entityRequester.getAllEntities()).thenReturn(entities);

    }
}
