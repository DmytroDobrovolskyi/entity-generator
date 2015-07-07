package com.softserve.entity.generator.service.salesforce;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.log4j.Logger;

public class Authenticator
{
    private static final Logger logger = Logger.getLogger(Authenticator.class);

    private String username;
    private String password;
    private String secToken;

    private String sessionId;

    public Authenticator(String username, String password, String secToken)
    {
        this.username = username;
        this.password = password;
        this.secToken = secToken;
    }

    public String getSessionId()
    {
        if (sessionId == null)
        {
            sessionId = login();
        }
        return sessionId;
    }

    private String login()
    {
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(username);
        config.setPassword(password + secToken);

        LoginResult loginResult = null;
        try
        {
            EnterpriseConnection connection = Connector.newConnection(config);
            loginResult = connection.login(username, password + secToken);
            System.out.println(loginResult.getSessionId());
        }
        catch (ConnectionException ex)
        {
            logger.error("Failed to establish connection", ex);
            System.exit(0);
        }
        return loginResult.getSessionId();
    }
}
