package com.softserve.entity.generator.salesforce;

import com.sforce.soap.apex.ExecuteAnonymousResult;
import com.sforce.soap.apex.SoapConnection;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.log4j.Logger;
import com.softserve.entity.generator.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;

public class WebServiceUtil
{
    private static final Logger logger = Logger.getLogger(WebServiceUtil.class);

    private static final Map<Credentials, WebServiceUtil> instanceCache = new HashMap<Credentials, WebServiceUtil>();

    //holds different connection to salesforce services
    private final Map<Class<?>, Object> connections = new HashMap<Class<?>, Object>();

    private LoginResult loginResult;
    private final Credentials credentials;

    private WebServiceUtil(Credentials credentials)
    {
        this.credentials = credentials;
    }

    public static WebServiceUtil getInstance(Credentials credentials)
    {
        WebServiceUtil instance = instanceCache.get(credentials);
        if (instance == null)
        {
            instance = new WebServiceUtil(credentials);
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
            Class<SoapConnection> soapConnectionClass = SoapConnection.class;
            SoapConnection connection = ReflectionUtil.castSafe(soapConnectionClass, connections.get(soapConnectionClass));
            if (connection == null)
            {
                LoginResult loginResult = getLoginResult();
                String serverUrl = loginResult.getServerUrl().replace("/Soap/u/", "/Soap/s/");

                ConnectorConfig connectorConfig = new ConnectorConfig();
                connectorConfig.setAuthEndpoint(serverUrl);
                connectorConfig.setServiceEndpoint(serverUrl);
                connectorConfig.setSessionId(loginResult.getSessionId());

                connections.put(soapConnectionClass, new SoapConnection(connectorConfig));
            }

            ExecuteAnonymousResult result = connection.executeAnonymous(apexCode);
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
    private LoginResult getLoginResult()
    {
        if (loginResult == null)
        {
            loginResult = login();
        }
        return loginResult;
    }

    private LoginResult login()
    {
        Class<PartnerConnection> partnerConnectionClass = PartnerConnection.class;
        PartnerConnection connection = ReflectionUtil.castSafe(partnerConnectionClass, connections.get(partnerConnectionClass));

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        String secToken = credentials.getToken();

        try
        {
            if (connection == null)
            {
                ConnectorConfig config = new ConnectorConfig();
                config.setUsername(username);
                config.setPassword(password + secToken);

                connection = Connector.newConnection(config);
                connections.put(partnerConnectionClass, connection);
            }
            loginResult = connection.login(username, password + secToken);
        }
        catch (ConnectionException ex)
        {
            logger.error("Failed to log in. Check your credentials");
            System.exit(1);
        }
        return loginResult;
    }
}
