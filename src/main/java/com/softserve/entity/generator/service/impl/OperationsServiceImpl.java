package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.repository.OperationsRepository;
import com.softserve.entity.generator.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationsServiceImpl implements OperationsService
{
    @Autowired
    private OperationsRepository operationsRepository;

    @Override
    @Transactional(value = "operationsTransactionManager", readOnly = true)
    public SalesforceCredentials findByOrganizationId(String organizationId)
    {
      return operationsRepository.findByOrganizationId(organizationId);
    }

    @Override
    @Transactional(value = "operationsTransactionManager")
    public void saveUser(SalesforceCredentials credentials)
    {
        operationsRepository.save(credentials);
    }

    @Override
    @Transactional(value = "operationsTransactionManager")
    public void deleteUser(String username)
    {
        operationsRepository.delete(
                operationsRepository.findById(username)
        );
    }

    @Override
    @Transactional(value = "operationsTransactionManager")
    public void updateUserData(SalesforceCredentials credentials)
    {
        operationsRepository.merge(credentials);
    }

    @Override
    @Transactional(value = "operationsTransactionManager", readOnly = true)
    public SalesforceCredentials findUser(String username)
    {
        return operationsRepository.findById(username);
    }
}
