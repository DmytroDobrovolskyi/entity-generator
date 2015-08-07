package com.softserve.entity.generator.service.applier;

import com.softserve.entity.generator.entity.production.Entity;
import com.softserve.entity.generator.service.applier.util.ProcedureGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Primary
public class EntityApplierImpl implements EntityApplier
{
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void applyAll(List<Entity> entities)
    {
        String createProcedureQueryString = ProcedureGenerator.generateProcedure(entities);

        entityManager.createNativeQuery(createProcedureQueryString).executeUpdate();
    }

    @Override
    @Transactional
    public void resolveNonExisting()
    {
        entityManager.createNativeQuery(
                "DECLARE @existingTables nvarchar(MAX) " +
                "SELECT @existingTables = COALESCE(@existingTables + ''',''' ,'') + name " +
                "FROM sys.tables " +
                "DECLARE @resolveNonExistingQuery nvarchar(MAX) = " +
                "( " +
                    "'UPDATE core_schema.ENTITY " +
                    "SET Is_Processing_Needed = " +
                    "WHERE Table_Name " +
                    "NOT IN (''' +  @existingTables + ''') '" +
                ") " +
                "EXEC sp_executesql @resolveNonExistingQuery"
        ).executeUpdate();
    }
}
