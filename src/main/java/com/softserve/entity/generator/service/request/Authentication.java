package com.softserve.entity.generator.service.request;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.softserve.entity.generator.config.soap.Config;

public class Authentication
{
    private EnterpriseConnection connection;
    private ConnectorConfig config;
    private LoginResult loginResult;

    public void login()
    {
        config = new ConnectorConfig();
        config.setUsername(Config.getConfig().getUserName());
        config.setPassword(Config.getConfig().getPassword());
        try
        {
            connection = Connector.newConnection(config);
            loginResult = connection.login(Config.getConfig().getUserName(), Config.getConfig().getPassword());
            System.out.println(loginResult.getSessionId());
        }
        catch (ConnectionException e1)
        {
            System.out.println("fail");
        }
    }
}
