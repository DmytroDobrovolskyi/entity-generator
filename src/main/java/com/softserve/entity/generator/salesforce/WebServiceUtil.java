package com.softserve.entity.generator.salesforce;

import com.sforce.soap.apex.ExecuteAnonymousResult;
import com.sforce.soap.apex.SoapConnection;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class WebServiceUtil
{
    private static final Logger logger = Logger.getLogger(WebServiceUtil.class);

    private static final Map<String, WebServiceUtil> credentialsMap = new HashMap<String, WebServiceUtil>();

    private LoginResult loginResult;

    /**
     * Returns session id after login process.
     *
     * @param credentials class-holder that contains username, password and security token
     * @return session id
     */
    public static String getSessionId(Credentials credentials)
    {
        return getLoginResult(credentials).getSessionId();
    }

    public static void executeApex(Credentials credentials, String apexCode)
    {
        LoginResult loginResult = getLoginResult(credentials);
        try
        {
            String serverUrl = loginResult.getServerUrl().replace("/Soap/u/", "/Soap/s/");

            ConnectorConfig connectorConfig = new ConnectorConfig();

            connectorConfig.setAuthEndpoint(serverUrl);
            connectorConfig.setServiceEndpoint(serverUrl);
            connectorConfig.setSessionId(loginResult.getSessionId());

            SoapConnection conn = new SoapConnection(connectorConfig);
            ExecuteAnonymousResult result = conn.executeAnonymous(apexCode);
            logger.info(result);
        }
        catch (ConnectionException ex)
        {
            logger.error("Failed to establish connection", ex);
            throw new AssertionError(ex);
        }
    }

    /**
     * Returns previously saved LoginResult object or log in first if such user does not exist in credentialsMap instance.
     *
     * @param credentials class-holder that contains username, password and security token
     * @return login result
     */
    private static LoginResult getLoginResult(Credentials credentials)
    {
        String username = credentials.getUsername();
        if (verify(username))
        {
            return credentialsMap.get(username).loginResult;
        }
        else
        {
            return login(username, credentials.getPassword(), credentials.getToken());
        }
    }

    private static LoginResult login(String username, String password, String secToken)
    {
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(username);
        config.setPassword(password + secToken);

        try
        {
            PartnerConnection connection = Connector.newConnection(config);
            LoginResult loginResult = connection.login(username, password + secToken);

            WebServiceUtil webServiceUtil = new WebServiceUtil();
            webServiceUtil.loginResult = loginResult;

            credentialsMap.put(username, webServiceUtil);

            return loginResult;
        }
        catch (ConnectionException ex)
        {
            logger.error("Failed to log in. Check your credentials");
            System.exit(0);
        }
        return null;
    }

    private static boolean verify(String username)
    {
        return credentialsMap.containsKey(username);
    }
}
