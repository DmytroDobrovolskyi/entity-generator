package com.softserve.entity.generator.service.salesforce.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Splitter
{
    public static List<String> splitSObjects(String stringifiedJson)
    {
        List<String> sObjects = new ArrayList<String>();
        stringifiedJson = stringifiedJson
                .replaceAll("\\}\\n.*},\\s\\{", "%")
                .replaceAll("__r\" : null\\n.*\\}, \\{", "__r\": \\[ \\] %");

        Collections.addAll(sObjects, stringifiedJson.split("%"));

        return sObjects;
    }
}
