package com.softserve.entity.generator.entity.operations;

import com.softserve.entity.generator.entity.DatabaseObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SALESFORCE_CREDENTIALS")
public class SalesforceCredentials extends DatabaseObject
{
    @Id
    @Column(name = "Username")
    private String username;

    @Column(name = "Organization_Id")
    private String organizationId;

    @Column(name = "Password")
    private String password;

    @Column(name = "Security_Token")
    private String securityToken;

    protected SalesforceCredentials() { }

    public SalesforceCredentials(String username, String password, String securityToken, String organizationId)
    {
        this.username = username;
        this.password = password;
        this.securityToken = securityToken;
        this.organizationId = organizationId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSecurityToken()
    {
        return securityToken;
    }

    public void setSecurityToken(String token)
    {
        this.securityToken = token;
    }

    public String getOrganizationId()
    {
        return organizationId;
    }

    public void setOrganizationId(String organizationId)
    {
        this.organizationId = organizationId;
    }
}
