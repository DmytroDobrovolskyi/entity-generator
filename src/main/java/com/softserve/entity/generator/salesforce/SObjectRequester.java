package com.softserve.entity.generator.salesforce;

import com.softserve.entity.generator.salesforce.util.SooqlQueryBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SObjectRequester<T>
{
    private static final Logger logger = Logger.getLogger(SObjectRequester.class);

    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v34.0/";
    private static final String TOTAL_SIZE_ZERO = "\"totalSize\" : 0";

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

        List<String> nonRelationFields = new ArrayList<String>();

        String sqlQuery = SooqlQueryBuilder.buildQuery(sObjectClass);

        System.out.println(sqlQuery);


        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "query/?q=" + sqlQuery);
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + WebServiceUtil.getSessionId(credentials)));
        httpGet.addHeader(new BasicHeader("X-PrettyPrint", "1"));

       /* try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String stringifiedResponse = EntityUtils.toString(response.getEntity());

            List<T> entities = new ArrayList<T>();

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
        }*/
        return null;
    }
}
