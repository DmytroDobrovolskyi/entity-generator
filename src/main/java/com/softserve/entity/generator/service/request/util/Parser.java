package com.softserve.entity.generator.service.request.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
    public void parse(String toParse)
    {
        System.out.println("////////");
        StringBuilder stringBuilder = new StringBuilder();
        String patternString = "\"attributes\"";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(toParse);
        while (matcher.find()){
            stringBuilder.append(matcher.group());
        }
        System.out.println(stringBuilder);
    }
}


/*String patternString = "\"attributes\" : \\{.*?\\}";*/
       /* String parsed = toParse.replaceAll("\"attributes\" : \\{.*?\\}","");*/