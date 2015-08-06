package com.softserve.entity.generator.service.impl;

import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.repository.UserDataRepository;
import com.softserve.entity.generator.service.UserDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDataServiceImpl implements UserDataService
{
    private static final Logger logger = Logger.getLogger(UserDataServiceImpl.class);

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    @Transactional(value = "operationsTransactionManager", readOnly = true)
    public SalesforceCredentials findByOrganizationId(String organizationId)
    {
      return userDataRepository.findByOrganizationId(organizationId);
    }

    @Override
    @Transactional(value = "operationsTransactionManager")
    public void saveUser(SalesforceCredentials credentials)
    {
        userDataRepository.save(credentials);
    }

    @Override
    @Transactional(value = "operationsTransactionManager")
    public void deleteUser(String username)
    {
        SalesforceCredentials userToDelete  = userDataRepository.findById(username);
        if (userToDelete == null)
        {
            logger.error("User does not exist");
            System.exit(1);
        }
        userDataRepository.delete(userToDelete);
    }

    @Override
    @Transactional(value = "operationsTransactionManager")
    public void updateUserData(SalesforceCredentials credentials)
    {
        userDataRepository.merge(credentials);
    }

    @Override
    @Transactional(value = "operationsTransactionManager", readOnly = true)
    public SalesforceCredentials findUser(String username)
    {
        return userDataRepository.findById(username);
    }
}
