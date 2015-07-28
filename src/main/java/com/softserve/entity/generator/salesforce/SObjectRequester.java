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

public class SObjectRequester<T>
{
    private static final Logger logger = Logger.getLogger(SObjectRequester.class);

    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v34.0/";
    private static final String NONE_OBJECTS = "\"totalSize\" : 0";

    private Credentials credentials;
    private Class<T> sObjectClass;
    private final  HttpClient httpClient = HttpClientBuilder.create().build();


    public SObjectRequester(Credentials credentials, Class<T> sObjectClass)
    {
        this.credentials = credentials;
        this.sObjectClass = sObjectClass;
    }

    public List<T> getAll()
    {
        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" +  SooqlQueryBuilder.buildQuery(sObjectClass));
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + WebServiceUtil.getSessionId(credentials)));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());

            if (stringifiedResponse.contains(NONE_OBJECTS))
            {
                return Collections.emptyList();
            }
            String javaStyleJson = SObjectJsonParser.parseSObjectJson(stringifiedResponse, sObjectClass);

            Gson gson = new Gson();

            return gson.fromJson(javaStyleJson, ParsingUtil.createParametrizedListType(sObjectClass));
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
