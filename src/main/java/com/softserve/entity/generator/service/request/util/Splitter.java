package com.softserve.entity.generator.service.request.util;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Splitter
{
    public List<String> splitSObjects(String stringifiedJson)
    {
        List<String> objectsList = new ArrayList<String>();
        stringifiedJson = stringifiedJson
                .replaceAll("\\}\\n.*},\\s\\{", "%")
                .replaceAll("__r\" : null\\n.*\\}, \\{", "__r\": \\[ \\] %");

        String[] sObject = stringifiedJson.split("%");

        Collections.addAll(objectsList, sObject);

        return objectsList;
    }
}
