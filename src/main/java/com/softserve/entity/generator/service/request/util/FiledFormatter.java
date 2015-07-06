package com.softserve.entity.generator.service.request.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.*;

public class FiledFormatter
{
    public static String toJavaStyle(String jsonToFormat)
    {
        StringBuilder formattedJson = new StringBuilder(jsonToFormat);
        Matcher matcher = Pattern.compile("(\"[A-Z].*__c\")|(\"[A-Z].*__r\")|(\"Name\")")
                                 .matcher(formattedJson);
        while (matcher.find())
        {
            formattedJson.setCharAt(
                    matcher.start() + 1,
                    toLowerCase(
                            formattedJson
                            .charAt(matcher.start() + 1)
                    )
            );
        }
        return  formattedJson
                .toString()
                .replaceAll("__c", "")
                .replaceAll("__r. :", "\" : \\[ \\{");
    }


}
