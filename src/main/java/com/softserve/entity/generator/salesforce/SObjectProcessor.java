package com.softserve.entity.generator.salesforce;

import com.google.gson.Gson;
import com.softserve.entity.generator.salesforce.util.ParsingUtil;
import com.softserve.entity.generator.salesforce.util.SObjectJsonParser;
import com.softserve.entity.generator.salesforce.util.SoqlQueryBuilder;
import com.softserve.entity.generator.util.ReflectionUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final Map<String, SObjectProcessor> instanceCache = new HashMap<String, SObjectProcessor>();

    private final String sessionId;
    private final Class<T> sObjectClass;
    private final HttpClient httpClient = HttpClientBuilder.create().build();

    private SObjectProcessor(String sessionId, Class<T> sObjectClass)
    {
        this.sessionId = sessionId;
        this.sObjectClass = sObjectClass;
    }

    public static <T> SObjectProcessor<T> getInstance(String sessionId, Class<T> sObjectClass)
    {
        @SuppressWarnings("unchecked")
        SObjectProcessor<T> instance = instanceCache.get(sessionId);

        if (instance == null || !instance.sObjectClass.equals(sObjectClass))
        {
            instance = new SObjectProcessor<T>(sessionId, sObjectClass);
            instanceCache.put(sessionId, instance);
        }
        return instance;
    }

    /**
     * Retrieve all object form Salesforce database by performing all required operation like making request
     * and parsing retrieved data to Java objects.
     *
     * @param fetchType EAGER — get full object with all relations, LAZY — get pure object
     * @return Java representation of Salesforce database objects
     */
    public List<T> getAll(FetchType fetchType)
    {
        String sObjectJson = getPureSObjectJson(SoqlQueryBuilder.buildQuery(sObjectClass, fetchType));

        if (sObjectJson.contains(NONE_OBJECTS))
        {
            return Collections.emptyList();
        }
        return new Gson().fromJson(
                SObjectJsonParser.toJavaStyleJsonArray(sObjectJson, sObjectClass),
                ReflectionUtil.getParametrizedType(sObjectClass, List.class)
        );
    }

    /**
     * Retrieve single object form Salesforce database by its id performing all required operation like making request
     * and parsing retrieved data to Java objects.
     *
     * @param conditionFieldName name of filed by which object will be retrieved
     * @param conditionFieldValue value of filed by which object will be retrieved
     * @param fetchType EAGER — get full object with all relations, LAZY — get pure object
     * @return Java representation of Salesforce database object with {@literal conditionFieldValue}, null if
     * none object were find
     */
    public T getOne(String conditionFieldName, String conditionFieldValue, FetchType fetchType)
    {
        String soqlQuery = SoqlQueryBuilder.buildQuery(sObjectClass, fetchType) + "+WHERE+" + conditionFieldName +
                "='" + conditionFieldValue + "'";
        String sObjectJson = getPureSObjectJson(soqlQuery);
        if (sObjectJson.contains(NONE_OBJECTS))
        {
            return null;
        }
        return new Gson().fromJson(SObjectJsonParser.toJavaStyleJson(sObjectJson, sObjectClass), sObjectClass);
    }

    public List<T> getAll(String conditionFieldName, List<String> conditionFieldValues, FetchType fetchType)
    {
        String soqlQuery = SoqlQueryBuilder.buildQuery(sObjectClass, fetchType) + "+WHERE+" + conditionFieldName +
                "+IN+(" + ParsingUtil.stringifyFieldsList(conditionFieldValues, "'", "'") + ")";
        String sObjectJson = getPureSObjectJson(soqlQuery);
        if (sObjectJson.contains(NONE_OBJECTS))
        {
            return Collections.emptyList();
        }
        return new Gson().fromJson(
                SObjectJsonParser.toJavaStyleJsonArray(sObjectJson, sObjectClass),
                ReflectionUtil.getParametrizedType(sObjectClass, List.class)
        );
    }

    private String getPureSObjectJson(String sooqlQuery)
    {
        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sooqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + sessionId));
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
