package com.softserve.entity.generator.service;

import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import org.springframework.stereotype.Service;

@Service
public interface UserDataService
{
    SalesforceCredentials findByOrganizationId(String id);

    void saveUser(SalesforceCredentials credentials);

    void deleteUser(String username);

    void updateUserData(SalesforceCredentials credentials);

    SalesforceCredentials findUser(String username);
}
