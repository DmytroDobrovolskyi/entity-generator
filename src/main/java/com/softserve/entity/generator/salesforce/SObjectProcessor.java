package com.softserve.entity.generator.salesforce;

import com.google.gson.Gson;
import com.softserve.entity.generator.salesforce.util.ParsingUtil;
import com.softserve.entity.generator.salesforce.util.SObjectJsonParser;
import com.softserve.entity.generator.salesforce.util.SooqlQueryBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * This class stands for retrieving objects from salesforce side.
 *
 * @param <T> type of object to retrieve
 */
public class SObjectProcessor<T>
{
    private static final Logger logger = Logger.getLogger(SObjectProcessor.class);

    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v34.0/";
    private static final String NONE_OBJECTS = "\"totalSize\" : 0";

    private Credentials credentials;
    private Class<T> sObjectClass;
    private final HttpClient httpClient = HttpClientBuilder.create().build();

    public SObjectProcessor(Credentials credentials, Class<T> sObjectClass)
    {
        this.credentials = credentials;
        this.sObjectClass = sObjectClass;
    }

    /**
     * Retrieve all object form Salesforce database by performing all required operation like making request
     * and parsing retrieved data to Java objects.
     *
     * @return Java representation of Salesforce database objects
     */
    public List<T> getAll()
    {
        String sObjectJson = getPureSObjectJson(SooqlQueryBuilder.buildQuery(sObjectClass));

        if (sObjectJson.contains(NONE_OBJECTS))
        {
            return Collections.emptyList();
        }

        return new Gson().fromJson(
                SObjectJsonParser.parseSObjectJsonArray(sObjectJson, sObjectClass),
                ParsingUtil.createParametrizedListType(sObjectClass)
        );
    }

    /**
     * Retrieve single object form Salesforce database by its id performing all required operation like making request
     * and parsing retrieved data to Java objects.
     *
     * @param salesforceId id of object in Salesforce database
     * @return Java representation of Salesforce database object with {@literal salesforceId}
     */
    public T getBySalesforceId(String salesforceId)
    {
        String sooqlQuery = SooqlQueryBuilder.buildQuery(sObjectClass) + "+WHERE+Id='" + salesforceId + "'";
        String sObjectJson = getPureSObjectJson(sooqlQuery);
        if (sObjectJson.contains(NONE_OBJECTS))
        {
            return null;
        }
        return new Gson().fromJson(SObjectJsonParser.parseSObjectJson(sObjectJson, sObjectClass), sObjectClass);
    }

    private String getPureSObjectJson(String sooqlQuery)
    {
        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sooqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + WebServiceUtil.getSessionId(credentials)));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        }
        catch (ClientProtocolException ex)
        {
            logger.error(ex);
            throw new AssertionError(ex);
        }
        catch (IOException ex)
        {
            logger.error(ex);
            throw new AssertionError(ex);
        }
    }
}
