package com.softserve.entity.generator.repository.impl;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.repository.OperationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Repository
public class OperationsRepositoryImpl extends CrudRepositoryImpl<SalesforceCredentials> implements OperationsRepository
{
    @Autowired
    @Qualifier(value = "operationsEntityManager")
    private EntityManager entityManager;

    @PostConstruct
    private void init()
    {
        super.resetEntityManager(entityManager);
    }

    @Override
    public SalesforceCredentials findByOrganizationId(String organizationId)
    {
        return entityManager.createQuery(
                "FROM SalesforceCredentials AS credentials" +
                "WHERE credentials.organizationId = '" + organizationId +"'",
                SalesforceCredentials.class
        ).getSingleResult();
    }

    public static void main(String[] args)
    {
        AppContextCache.getContext(AppConfig.class).getBean(OperationsRepository.class).findByOrganizationId("");
    }
}
