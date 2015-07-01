package com.softserve.entity.generator.service.request;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

public class EntityRequester {
    private String accessToken;
    private static final String BASE_URL = "https://emea.salesforce.com/services/data/";
    private static final String API_VERSION = "v33.0/";

    public void getfullEntityInfo() {
        String accessToken = new Authentication().login();
        CloseableHttpClient httpPost = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(BASE_URL + API_VERSION + "sobjects/Entity__c");
        httpGet.addHeader(new BasicHeader("Authorization", "OAuth " + accessToken));

    }
}
