package com.softserve.entity.generator.service.request.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{

    public String parseSObjectJson(String sObjectJson, Class fieldName) {
        String removeRecords = "\"records\" : \\[ \\{.*? \\},";
        sObjectJson = Pattern.compile(removeRecords, Pattern.DOTALL).matcher(sObjectJson).replaceAll("");

        String removeAttributes = "\"attributes\" : \\{.*?\\},";
        sObjectJson = Pattern.compile(removeAttributes, Pattern.DOTALL).matcher(sObjectJson).replaceAll("");

            sObjectJson = sObjectJson
                                       .replaceAll("[A-Z].*__r", fieldName.getClass().getSimpleName().toLowerCase()+"s")
                                       .replaceAll("__c","");



        System.out.println("-----------------");
        System.out.println(sObjectJson);
        System.out.println("-----------------");

        return sObjectJson;
    }
}
