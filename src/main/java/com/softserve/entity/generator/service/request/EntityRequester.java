package com.softserve.entity.generator.service.request;

import com.google.gson.Gson;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.service.request.util.Parser;
import com.softserve.entity.generator.service.request.util.Splitter;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityRequester
{
    private String accessToken = new Authentication().login();

    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v33.0/";
    private Splitter splitter = new Splitter();
    private Parser parser = new Parser();

    public void getAllEntitiesWithFields()
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        String sqlQuery = "SELECT+EntityId__c,TableName__c,Name,(Select+ColumnName__c,Type__c,FieldId__c," +
                "Entity__c,Name+From+Fields__r)" +
                "+FROM+Entity__c";

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + accessToken));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());
            stringifiedResponse = stringifiedResponse.replaceAll("\\}\\n.*},\\s\\{", "%");

            List<String> listOfParsedObjects = new ArrayList<String>();

            for (String s : splitter.splitSObjects(stringifiedResponse))
            {
                listOfParsedObjects.add(parser.parseSObjectJson(s, Field.class.getClass()));
            }

            Gson gson = new Gson();

            List<Entity> entities = new ArrayList<Entity>();

            for (String parsedString : listOfParsedObjects)
            {
                Entity entity = gson.fromJson(parsedString, Entity.class);
                entities.add(entity);

                for (Field field : entity.getFields())
                {
                    field.setEntity(entity);
                }
            }
            System.out.println(entities.size());
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

    private List<String> splitSObjects(String stringifiedJson)
    {
        List<String> objectsList = new ArrayList<String>();
        String[] sObject = stringifiedJson.split("%");

        for (String s : sObject)
        {
            objectsList.add(s);
        }

        return objectsList;
    }

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
            throw new AssertionError(ex);
        }
        catch (IOException ex)
        {
            throw new AssertionError(ex);
        }
    }

    public void getEntityByExternalId(String id)
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        String sqlQuery = "SELECT+EntityId__c,TableName__c,Name,(Select+ColumnName__c," +
                "Type__c,FieldId__c,Entity__c,Name+From+Fields__r)" +
                "+FROM+Entity__c+" +
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
            throw new AssertionError(ex);
        }
        catch (IOException ex)
        {
            throw new AssertionError(ex);
        }
    }

    public void getById(String id)
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION +
                "sobjects/Entity__c/" + id);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + accessToken));

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());
            System.out.println();
            System.out.println("//////");
            System.out.println(stringifiedResponse);
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
}
