package com.softserve.entity.generator.service.request.util;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
    private static final Logger logger = Logger.getLogger(Parser.class);

    public void parse(String data)
    {
        data = data.replaceAll("\\s", "$");
        logger.info(data);
        Matcher matcher = Pattern.compile(",", Pattern.DOTALL).matcher(data);

        while (matcher.find())
        {
            logger.info(matcher.group());
        }
    }
}
