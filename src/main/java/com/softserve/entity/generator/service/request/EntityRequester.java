package com.softserve.entity.generator.service.request;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class EntityRequester
{
    private String accessToken;
    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v33.0/";

    public void getFullEntityInfo()
    {
        String accessToken = new Authentication().login();

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "sobjects/Entity__c");
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + accessToken));


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
}
