package com.softserve.entity.generator;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.service.applier.Applier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplierTest {

    @Mock
    private Applier applier;

    private Entity entity = new Entity("","");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void invocationTest() {
        applier.createTable(generateEntity());
        verify(applier).createTable(generateEntity());
    }

    @Test
    public void testMethodOnException() {
        Mockito.doThrow(new RuntimeException()).when(applier).createTable(generateEntity());
    }

    @Test
    public void testMethodOnCorrectArgument() {
        assertEquals("FIELD", generateEntity().getTableName());
    }

    private Entity generateEntity() {
        entity.setTableName("FIELD");

        Field firstField = new Field();
        firstField.setColumnName("First_Column");
        firstField.setType("int");

        Field secondField = new Field();
        secondField.setColumnName("Second_Column");
        secondField.setType("int");

        Field thirdField = new Field();
        thirdField.setColumnName("Third_Column");
        thirdField.setType("int");

        Set<Field> fields = new HashSet<Field>();
        fields.add(firstField);
        fields.add(secondField);
        fields.add(thirdField);
        entity.setFields(fields);

        return entity;
    }
}
