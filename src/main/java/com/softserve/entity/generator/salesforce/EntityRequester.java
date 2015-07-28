package com.softserve.entity.generator.salesforce;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import com.softserve.entity.generator.salesforce.util.Parser;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EntityRequester
{
    private static final Logger logger = Logger.getLogger(EntityRequester.class);

    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v34.0/";

    private Credentials credentials;

    private static final String CUSTOM_FIELDS;
    private static final String RELATION;
    private static final String RELATION_CUSTOM_FIELDS;
    private static final String ENTITY_NAME;
    private static final String TOTAL_SIZE_ZERO = "\"totalSize\" : 0";

    static
    {
        ENTITY_NAME = Entity.class.getSimpleName() + "__c";
        CUSTOM_FIELDS = ColumnsRegister.getCustomFieldsMap().get(Entity.class);
        RELATION = Field.class.getSimpleName() + "s__r";
        RELATION_CUSTOM_FIELDS = ColumnsRegister.getCustomFieldsMap().get(Field.class);
    }

    public EntityRequester(Credentials credentials)
    {
        this.credentials = credentials;
    }

    public List<Entity> getAllEntities()
    {
        HttpClient httpClient = HttpClientBuilder.create().build();

        List<String> nonRelationFields = new ArrayList<String>();

        String sqlQuery =
                          "SELECT+Name," + CUSTOM_FIELDS + "," +
                         "(" +
                              "SELECT+Name," + RELATION_CUSTOM_FIELDS + "+" +
                              "FROM+" + RELATION +
                          ")+" +
                          "FROM+" + ENTITY_NAME;

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + WebServiceUtil.getSessionId(credentials)));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));

        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());

            List<Entity> entities = new ArrayList<Entity>();

            if (stringifiedResponse.contains(TOTAL_SIZE_ZERO))
            {
                return entities;
            }

            String parsedJson = Parser.parseSObjectJson(stringifiedResponse, Entity.class, Field.class);

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Entity>>() {}.getType();

            List<Entity> entityList = gson.fromJson(parsedJson, listType);

            for (Entity entity : entityList)
            {
                for(Field field : entity.getFields())
                {
                    field.setEntity(entity);
                }
                entities.add(entity);
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
}
