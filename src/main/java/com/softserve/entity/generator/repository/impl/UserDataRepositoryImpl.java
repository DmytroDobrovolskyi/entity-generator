package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Repository
public class UserDataRepositoryImpl extends CrudRepositoryImpl<SalesforceCredentials> implements UserDataRepository
{
    @Autowired
    @Qualifier(value = "operationsEntityManager")
    private EntityManager entityManager;

    @PostConstruct
    private void init()
    {
        super.resetEntityManager(entityManager);
        super.setObjectClassToken(SalesforceCredentials.class);
    }

    @Override
    public SalesforceCredentials findByOrganizationId(String organizationId)
    {
        return entityManager.createQuery(
                "FROM SalesforceCredentials AS credentials " +
                        "WHERE credentials.organizationId = '" + organizationId + "'",
                SalesforceCredentials.class
        ).getSingleResult();
    }
}
