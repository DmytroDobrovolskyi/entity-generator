package com.softserve.entity.generator;

import com.softserve.entity.generator.config.MockConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.applier.Applier;
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
    //TODO: REWRITE TEST according to new Applier
    public void testApply()
    {
       /* String selectProcedureQuery =
                        "SELECT cast(name as varchar) " +
                        "FROM sys.objects " +
                        "WHERE name ='" + PROCEDURE_NAME + "'";
        Query selectProcedureQueryMock = mockQuery(selectProcedureQuery);

        List resultList = mockResultList(selectProcedureQueryMock);
        assertTrue(resultList.size() != 0);

        String dropProcedureQuery = "DROP PROCEDURE" + resultList;
        Query dropProcedureQueryMock = mockQuery(dropProcedureQuery);

        Entity entity = generateEntity();

        String createProcedureQuery = generateProcedure(entity);
        Query createProcedureQueryMock = mockQuery(createProcedureQuery);

        String executeProcedureQuery = "EXEC " + PROCEDURE_NAME;
        Query executeProcedureQueryMock = mockQuery(executeProcedureQuery);

        applier.apply(entity);

        verify(entityManager).createNativeQuery(selectProcedureQuery);
        verify(selectProcedureQueryMock).getResultList();

        verify(entityManager).createNativeQuery(dropProcedureQuery);
        verify(dropProcedureQueryMock).executeUpdate();

        verify(entityManager).createNativeQuery(createProcedureQuery);
        verify(createProcedureQueryMock).executeUpdate();

        //TODO: procedure generator assert
        verify(entityManager).createNativeQuery(executeProcedureQuery);
        verify(executeProcedureQueryMock).executeUpdate();*/
    }

    private Query mockQuery(String query)
    {
        Query queryMock = mock(Query.class);

        doReturn(queryMock)
                .when(entityManager)
                .createNativeQuery(query);

        return queryMock;
    }

    private List mockResultList(Query selectProcedureQueryMock)
    {
        List resultList = Collections.singletonList(PROCEDURE_NAME);
        doReturn(resultList)
                .when(selectProcedureQueryMock)
                .getResultList();

        return resultList;
    }
}
