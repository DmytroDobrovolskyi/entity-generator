package com.softserve.entity.generator;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.applier.Applier;
import com.softserve.entity.generator.service.applier.util.ProcedureGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

import static com.softserve.entity.generator.entity.util.EntityGenerator.generateEntity;
import static com.softserve.entity.generator.service.applier.util.ProcedureGenerator.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplierTest
{

    @Autowired
    private Applier<Entity> applier;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testApply()
    {
    }

    @Test
    public void testProcedureForEqulity()
    {
        Entity entity = generateEntity();
        String createProcedureQuery = ProcedureGenerator.generateProcedure(entity).replaceAll("\\s","");
        assertEquals(generateProcedure(),createProcedureQuery);
    }

    private Query mockQuery(String query)
    {
        Query queryMock = mock(Query.class);

        doReturn(queryMock)
                .when(entityManager)
                .createNativeQuery(query);

        return queryMock;
    }

    private String generateProcedure(){
        String procedureQuery ="IF  EXISTS (SELECT *\n" +
                "            FROM sys.procedures\n" +
                "            WHERE name ='myProcedure'\n" +
                "            )\n" +
                "DROP PROCEDURE myProcedure\n" +
                "BEGIN\n" +
                "EXEC ('\n" +
                "CREATE PROCEDURE myProcedure\n" +
                "AS\n" +
                "CREATE TABLE [core_schema].NEW_TABLE(\n" +
                "            First_Column1 int,Second_Column1 int,Third_Column1 int\n" +
                "    );\n" +
                "')\n" +
                "END\n" +
                "BEGIN\n" +
                "EXEC ('myProcedure')\n" +
                "END\n";
    return  procedureQuery.replaceAll("\\s","");
    }
}
