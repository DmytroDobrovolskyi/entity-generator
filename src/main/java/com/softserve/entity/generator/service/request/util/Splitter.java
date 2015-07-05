package com.softserve.entity.generator.service.request.util;

import java.util.ArrayList;
import java.util.List;

public class Splitter
{
    public List<String> splitSObjects(String stringifiedJson)
    {
        List<String> objectsList = new ArrayList<String>();
        String[] sObject = stringifiedJson.split("%");

        for (String s : sObject)
        {
            objectsList.add(s);
        }

        return objectsList;
    }
}
