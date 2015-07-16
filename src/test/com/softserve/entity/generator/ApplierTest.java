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

import static com.softserve.entity.generator.entity.util.EntityGenerator.generateEntity;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = MockConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplierTest
{
    private static final int columnQuantity = 3;
    private static final String emptySpaceRegex = "\\s";
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
        Entity entity = generateEntity();

        String createProcedureQuery = ProcedureGenerator.generateProcedure(entity);

        /*assertEquals
                (
                        this.generateProcedure(),
                        createProcedureQuery
                                .replaceAll("First_Column int", "")
                                .replaceAll("Second_Column int", "")
                                .replaceAll("Third_Column int", "")
                                .replaceAll(",", "")
                                .replaceAll(emptySpaceRegex, "")
                );*/
        assertEquals(columnQuantity, entity.getFields().size());

        Query procedureQueryMock = mockQuery(createProcedureQuery);

        applier.apply(entity);
        verify(entityManager).createNativeQuery(createProcedureQuery);
        verify(procedureQueryMock).executeUpdate();
    }

    private Query mockQuery(String query)
    {
        Query queryMock = mock(Query.class);

        doReturn(queryMock)
                .when(entityManager)
                .createNativeQuery(query);

        return queryMock;
    }

    private String generateProcedure()
    {
        String procedureQuery =
                "IF  EXISTS " +
                        "(" +
                            "SELECT * " +
                            "FROM sys.procedures" +
                            "WHERE name ='myProcedure'" +
                        ")" +
                        "DROP PROCEDURE myProcedure" +
                        "BEGIN" +
                        "EXEC " +
                        "('" +
                        "CREATE PROCEDURE myProcedure" +
                        "AS" +
                        "CREATE TABLE [core_schema].NEW_TABLE" +
                        "(" +
                        "    );" +
                        "')" +
                        "END" +
                        "BEGIN" +
                        "EXEC " +
                        "(" +
                            "'myProcedure'" +
                        ")" +
                        "END";

        return procedureQuery.replaceAll(emptySpaceRegex, "");
    }
}
