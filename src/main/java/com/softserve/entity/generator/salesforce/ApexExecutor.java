package com.softserve.entity.generator.salesforce;

import com.sforce.soap.apex.ExecuteAnonymousResult;
import com.sforce.soap.apex.SoapConnection;
import com.sforce.soap.partner.LoginResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ApexExecutor
{
    private static final Logger logger = Logger.getLogger(ApexExecutor.class);

    public void executeApex(LoginResult loginResult, String apexCode)
    {
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
}
