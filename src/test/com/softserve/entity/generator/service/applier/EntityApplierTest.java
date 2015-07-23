package com.softserve.entity.generator.service.applier;

import com.softserve.entity.generator.config.MockServiceConfig;
import com.softserve.entity.generator.entity.Entity;
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
import java.util.ArrayList;
import java.util.List;

import static com.softserve.entity.generator.entity.util.TestEntityGenerator.generateEntity;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = MockServiceConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityApplierTest
{
    private static final int COLUMN_QUANTITY = 3;
    private static final String EMPTY_SPACE_REGEX = "\\s";

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
    public void testApplyAll()
    {
        Entity entity = generateEntity();
        List<Entity> entityList = new ArrayList<Entity>();
        entityList.add(entity);

        String createProcedureQuery = ProcedureGenerator.generateProcedure(entityList);

        assertEquals
                (
                        this.generateProcedure()
                                .replaceAll(EMPTY_SPACE_REGEX, ""),
                        createProcedureQuery
                                .replaceAll("First_Column int,", "")
                                .replaceAll("Second_Column int,", "")
                                .replaceAll("Third_Column int,", "")
                                .replaceAll(EMPTY_SPACE_REGEX, "")
                );

        assertEquals(COLUMN_QUANTITY, entity.getFields().size());

        Query procedureQueryMock = mockQuery(createProcedureQuery);
        applier.applyAll(entityList);
        verify(entityManager).createNativeQuery(createProcedureQuery);
        verify(procedureQueryMock).executeUpdate();
    }

    private Query mockQuery(String query)
    {
        Query queryMock = mock(Query.class);

        when(entityManager.createNativeQuery(query))
                .thenReturn(queryMock);

        return queryMock;
    }

    private String generateProcedure()
    {
        return " IF  EXISTS\n" +
                "         (\n" +
                "             SELECT *\n" +
                "             FROM sys.procedures\n" +
                "             WHERE name ='generateTable'\n" +
                "         )\n" +
                "         DROP PROCEDURE generateTable\n" +
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
                "         WHERE TABLES.name NOT IN ('ENTITY', 'FIELD', 'NEW_TABLE')\n" +
                "         IF @tablesToDelete IS NULL\n" +
                "             SET @tablesToDelete = ''\n" +
                "         DECLARE @tablesToDeleteLength int = LEN(@tablesToDelete)\n" +
                "         DECLARE @changedTablesLength int = LEN('')\n" +
                "         IF @tablesToDeleteLength > 0  AND @changedTablesLength > 0\n" +
                "             SET @tablesToDelete =  @tablesToDelete + ',  '\n" +
                "         ELSE IF @tablesToDeleteLength = 0  AND @changedTablesLength > 0\n" +
                "             SET @tablesToDelete =  ' '\n" +
                "         IF LEN(@tablesToDelete) > 0\n" +
                "             SET @procedureQuery = @procedureQuery +\n" +
                "             (\n" +
                "                 'DROP TABLE ' + @tablesToDelete + ' '\n" +
                "             )\n" +
                "                 SET @procedureQuery = @procedureQuery +\n" +
                "                 (\n" +
                "                     'IF (''NEW_TABLE'')\n" +
                "                      NOT IN\n" +
                "                     (\n" +
                "                         SELECT name\n" +
                "                         FROM sys.tables\n" +
                "                     )\n" +
                "                     CREATE TABLE mdd.NEW_TABLE\n" +
                "                     (\n" +
                "                     )'\n" +
                "                 )\n" +
                "         EXEC sp_executesql @procedureQuery\n" +
                "         IF LEN(@procedureQuery) > @initialProcedureQueryLength\n" +
                "             EXEC ('generateTable')";
    }
}
