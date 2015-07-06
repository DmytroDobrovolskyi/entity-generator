package com.softserve.entity.generator.service.request;

import com.google.gson.Gson;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.service.request.util.FieldsRegister;
import com.softserve.entity.generator.service.request.util.Parser;
import com.softserve.entity.generator.service.request.util.Splitter;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityRequester
{
    private static final Logger logger = Logger.getLogger(EntityRequester.class);

    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v33.0/";

    private final Splitter splitter;
    private final Parser parser;
    private final Authenticator authenticator;

    private final String customFields;
    private final String relation;
    private final String relationCustomFileds;

    public EntityRequester(String username, String password, String secToken)
    {
        splitter = new Splitter();
        parser = new Parser();
        authenticator = new Authenticator(username, password, secToken);

        customFields = FieldsRegister.getCustomFieldsMap().get(Entity.class);
        relation = FieldsRegister.getRelationsMap().get(Entity.class);
        relationCustomFileds = FieldsRegister.getCustomFieldsMap().get(Field.class);
    }

    public List<Entity> getAllEntities()
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        String entityName = Entity.class.getSimpleName();

        String sqlQuery =
                        "SELECT+Name," + customFields + "," +
                        "(" +
                            "SELECT+Name," + relationCustomFileds + "+" +
                            "FROM+" + relation +
                        ")+" +
                        "FROM+" + entityName;

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + authenticator.getSessionId()));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());

            List<String> parsableSObjects = new ArrayList<String>();

            for (String parsableSObject : splitter.splitSObjects(stringifiedResponse))
            {
                parsableSObjects.add(parser
                                .parseSObjectJson(parsableSObject)
                );
            }

            Gson gson = new Gson();

            List<Entity> entities = new ArrayList<Entity>();

            for (String parsableSObject : parsableSObjects)
            {
                Entity entity = gson.fromJson(parsableSObject, Entity.class);
                entities.add(entity);

                if (entity.getFields() != null)
                {
                    for (Field field : entity.getFields())
                    {
                        field.setEntity(entity);
                    }
                }
            }
            return entities;
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

    public String getFullInfo()
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "sobjects/Entity__c");
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + authenticator.getSessionId()));
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

    public Entity getEntityByExternalId(String id)
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        String entityName = Entity.class.getSimpleName();

        String sqlQuery =
                        "SELECT+Name," + customFields + "," +
                        "(" +
                            "SELECT+Name," + relationCustomFileds + "+" +
                            "FROM+" + relation +
                        ")+" +
                        "FROM+" + entityName + "+" +
                        "WHERE+" + entityName + "Id__c" + "='" + id + "'";

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + authenticator.getSessionId()));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());

            String parsableSObject = parser.parseSObjectJson(stringifiedResponse);

            Gson gson = new Gson();

            Entity entity = gson.fromJson(parsableSObject, Entity.class);

            if (entity.getFields() != null)
            {
                for (Field field : entity.getFields())
                {
                    field.setEntity(entity);
                }
            }

            return entity;
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
