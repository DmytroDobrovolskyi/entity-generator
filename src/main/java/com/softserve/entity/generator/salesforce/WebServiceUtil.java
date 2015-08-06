package com.softserve.entity.generator.salesforce;

import com.sforce.soap.apex.ExecuteAnonymousResult;
import com.sforce.soap.apex.SoapConnection;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class WebServiceUtil
{
    private static final Logger logger = Logger.getLogger(WebServiceUtil.class);

    private static final Map<SalesforceCredentials, WebServiceUtil> instanceCache = new HashMap<SalesforceCredentials, WebServiceUtil>();

    private LoginResult loginResult;
    private PartnerConnection partnerConnection;
    private SoapConnection soapConnection;
    private final SalesforceCredentials salesforceCredentials;

    private WebServiceUtil(SalesforceCredentials salesforceCredentials)
    {
        this.salesforceCredentials = salesforceCredentials;
    }

    public static WebServiceUtil getInstance(SalesforceCredentials salesforceCredentials)
    {
        WebServiceUtil instance = instanceCache.get(salesforceCredentials);
        if (instance == null)
        {
            instance = new WebServiceUtil(salesforceCredentials);
        }
        return instance;
    }

    /**
     * Returns session id after login process.
     *
     * @return session id
     */
    public String getSessionId()
    {
        return getLoginResult().getSessionId();
    }

    public void executeApex(String apexCode)
    {
        try
        {
            if (soapConnection == null)
            {
                LoginResult loginResult = getLoginResult();
                String serverUrl = loginResult.getServerUrl().replace("/Soap/u/", "/Soap/s/");

                ConnectorConfig connectorConfig = new ConnectorConfig();
                connectorConfig.setAuthEndpoint(serverUrl);
                connectorConfig.setServiceEndpoint(serverUrl);
                connectorConfig.setSessionId(loginResult.getSessionId());

                soapConnection = new SoapConnection(connectorConfig);
            }

            ExecuteAnonymousResult result = soapConnection.executeAnonymous(apexCode);
            logger.info(result);
        }
        catch (ConnectionException ex)
        {
            logger.error("Failed to establish connection", ex);
            throw new AssertionError(ex);
        }
    }

    /**
     * Returns previously saved LoginResult object or log in first if such user does not exist in instanceCache instance.
     *
     * @return login result
     */
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
        String username = salesforceCredentials.getUsername();
        String password = salesforceCredentials.getPassword();
        String secToken = salesforceCredentials.getSecurityToken();
        try
        {
            if (partnerConnection == null)
            {
                ConnectorConfig config = new ConnectorConfig();
                config.setUsername(username);
                config.setPassword(password + secToken);

                partnerConnection = Connector.newConnection(config);
            }
            loginResult = partnerConnection.login(username, password + secToken);
        }
        catch (ConnectionException ex)
        {
            logger.error("Failed to log in. Check your internet connection and ensure that this user credentials was properly inserted in operations database");
            System.exit(1);
        }
        return loginResult;
    }
}
