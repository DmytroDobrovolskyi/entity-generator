package com.softserve.entity.generator.service.salesforce;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.log4j.Logger;

public class Authenticator
{
    private static final Logger logger = Logger.getLogger(Authenticator.class);

    private String username;
    private String password;
    private String secToken;

    private LoginResult loginResult;

    public Authenticator(String username, String password, String secToken)
    {
        this.username = username;
        this.password = password;
        this.secToken = secToken;
    }

    public LoginResult getLoginResult()
    {
        if (loginResult == null)
        {
            loginResult = login();
        }
        return loginResult;
    }

    private LoginResult login()
    {
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(username);
        config.setPassword(password + secToken);

        try
        {
            PartnerConnection connection = Connector.newConnection(config);

            return connection.login(username, password + secToken);
        }
        catch (ConnectionException ex)
        {
            logger.error("Failed to establish connection", ex);
            throw new AssertionError(ex);
        }
    }
}
