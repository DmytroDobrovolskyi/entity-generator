package com.softserve.entity.generator.service.request.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Parser
{
    private static final Logger logger = Logger.getLogger(Parser.class);

    public String parseSObjectJson(String sObjectJson)
    {
        sObjectJson = Pattern.compile("\"records\" : \\[ \\{.*? \\},", Pattern.DOTALL)
                .matcher(sObjectJson)
                .replaceAll("");

        sObjectJson = Pattern.compile("\"attributes\" : \\{.*?\\},", Pattern.DOTALL)
                .matcher(sObjectJson)
                .replaceAll("");

        sObjectJson = sObjectJson
                .replaceAll("\\{\\n.*\"totalSize\" : .,\\n.*\"done\" :.*,", "")
                .replaceAll("\\]", "] }")
                .replaceAll("\\}\\n.*} \\] \\}\\n.*\\}", "")
                .replaceAll("\\{\\n.*\\n.*\\n.*\\{", "\\{");

        sObjectJson = FiledFormatter.toJavaStyle(sObjectJson)
                .replaceAll("\\{ null\\n.*\\} \\] \\}", "\\]");

        return "{ " + sObjectJson;
    }
}
