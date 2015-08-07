package com.softserve.entity.generator.repository;

import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends CrudRepository<SalesforceCredentials>
{
    SalesforceCredentials findByOrganizationId(String organizationId);
}
