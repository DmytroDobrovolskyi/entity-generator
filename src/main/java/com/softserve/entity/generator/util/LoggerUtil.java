package com.softserve.entity.generator.util;

import org.apache.log4j.Logger;

public class LoggerUtil
{
    public static Logger logger(Object object)
    {
        return Logger.getLogger(object.getClass());
    }
}
