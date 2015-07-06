package com.softserve.entity.generator.service.request;

import com.softserve.entity.generator.service.request.util.Parser;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

public class EntityRequester
{
    private String accessToken = new Authentication().login();
    private static final Logger logger = Logger.getLogger(EntityRequester.class);

    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v33.0/";

    public void getFullEntityInfo()
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "sobjects/Entity__c");
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + accessToken));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        catch (ClientProtocolException ex)
        {
            logger.error(ex);
        }
        catch (IOException ex)
        {
            logger.error(ex);
        }
    }

    public void getEntityByExternalId(String id)
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        String sqlQuery = "SELECT+EntityId__c,TableName__c,Name," +
                "(" +
                    "Select+ColumnName__c,Type__c,FieldId__c,Entity__c,Name+From+Fields__r" +
                ")+" +
                "FROM+Entity__c+" +
                "WHERE+EntityId__c='" + id + "'";

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + accessToken));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());
            System.out.println(stringifiedResponse);
        }
        catch (ClientProtocolException ex)
        {
            logger.error(ex);
        }
        catch (IOException ex)
        {
            logger.error(ex);
        }
    }

    public String getAllEntities()
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        String sqlQuery = "SELECT+EntityId__c,TableName__c,Name," +
                "(" +
                    "Select+ColumnName__c,Type__c,FieldId__c,Entity__c,Name+From+Fields__r" +
                ")+" +
                "FROM+Entity__c";

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + accessToken));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());

            return stringifiedResponse;
        }
        catch (ClientProtocolException ex)
        {
            throw new AssertionError(ex);
        }
        catch (IOException ex)
        {
            throw new AssertionError(ex);
        }
    }

    public static void main(String[] args)
    {
        String result = new EntityRequester().getAllEntities();
      logger.info(result);

    }
}
