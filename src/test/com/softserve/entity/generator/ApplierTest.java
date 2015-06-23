package com.softserve.entity.generator;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.applier.Applier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.softserve.entity.generator.entity.util.EntityGenerator.generateEntity;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplierTest {

    @Autowired
    private Applier<Entity> applier;

    private final Entity entity = generateEntity();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void invocationTest() {
        applier.apply(entity);
        verify(applier).apply(entity);
    }

    //TODO ???
    @Test
    public void testMethodOnException() {
        Mockito.doThrow(new RuntimeException())
                .when(applier)
                .apply(entity);
    }

    @Test
    public void testMethodOnCorrectArgument() {
        assertEquals("NEW_TABLE", entity.getTableName());
    }
}
