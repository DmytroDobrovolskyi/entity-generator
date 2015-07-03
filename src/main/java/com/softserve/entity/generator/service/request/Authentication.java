package com.softserve.entity.generator.service.request;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.log4j.Logger;

public class Authentication
{
    private LoginResult loginResult;
    private static final String USERNAME = "gwini777@gmail.com";
    private static final String PASSWORD = "Nap18129523$veOlEXt4dfLlIEqYTp8xCNT6I";

    private static final Logger logger = Logger.getLogger(Authentication.class);

    public String login()
    {
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        LoginResult loginResult = null;
        try
        {
            EnterpriseConnection connection = Connector.newConnection(config);
            loginResult = connection.login(USERNAME, PASSWORD);
        }
        catch (ConnectionException ex)
        {
            logger.error(ex);
        }
        return loginResult.getSessionId();
    }
}
