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

        System.out.println(createProcedureQuery);
        assertEquals
                (
                        this.generateProcedure()
                                .replace(emptySpaceRegex,"").trim(),
                        createProcedureQuery
                                .replaceAll(emptySpaceRegex, "").trim()
                );

        /*assertEquals(columnQuantity, entity.getFields().size());*/

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
        String procedureQuery =" IF  EXISTS\n" +
                "         (\n" +
                "             SELECT *\n" +
                "             FROM sys.procedures\n" +
                "             WHERE name ='generateTable'\n" +
                "         )\n" +
                "    [     DROP PROCEDURE generateTable\n" +
                "         DECLARE @procedureQuery nvarchar(MAX) = 'CREATE PROCEDURE generateTable AS '\n" +
                "         DECLARE @initialProcedureQueryLength int = LEN(@procedureQuery)\n" +
                "         DECLARE @tablesToDelete nvarchar(MAX)\n" +
                "         SELECT @tablesToDelete = COALESCE(@tablesToDelete + ', ','') + SCHEMAS.name, @tablesToDelete = COALESCE(@tablesToDelete + '.','') + TABLES.name\n" +
                "         FROM\n" +
                "             sys.tables TABLES\n" +
                "                 JOIN\n" +
                "             sys.schemas SCHEMAS\n" +
                "         ON\n" +
                "         TABLES.schema_id = SCHEMAS.schema_id\n" +
                "         WHERE TABLES.name NOT IN ('ENTITY', 'FIELD')\n" +
                "         SET @tablesToDelete = @tablesToDelete + ''\n" +
                "         DECLARE @tablesToDeleteLength int = LEN(@tablesToDelete)\n" +
                "         DECLARE @changedTablesLength int = LEN('')\n" +
                "         IF @tablesToDeleteLength > 0  AND @changedTablesLength > 0\n" +
                "             SET @tablesToDelete =  @tablesToDelete + ',  '\n" +
                "         ELSE IF @tablesToDeleteLength = 0  AND @changedTablesLength > 0\n" +
                "             SET @tablesToDelete =  ' '\n" +
                "         IF LEN(@tablesToDelete) > 0\n" +
                "             SET @procedureQuery = @procedureQuery +\n" +
                "                 'DROP TABLE ' + @tablesToDelete + ' '\n" +
                "             )\n" +
                "         EXEC sp_executesql @procedureQuery\n" +
                "         IF LEN(@procedureQuery) > @initialProcedureQueryLength\n" +
                "             EXEC ('generateTable')";

        return procedureQuery.replaceAll(emptySpaceRegex, "");
    }
}
