package com.softserve.entity.generator.service.request;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class EntityRequester
{
    private String accessToken;
    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";

    public void getfullEntityInfo()
    {

        CloseableHttpClient httpPost = HttpClientBuilder.create().build();
     /*   HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "sobjects/Line_Item__c");*/
}
}
