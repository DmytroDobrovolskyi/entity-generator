package com.softserve.entity.generator.app.operations;

import com.softserve.entity.generator.app.util.UserDataUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.service.UserDataService;

public class UpdateUser
{
    public static void main(String[] args)
    {
        SalesforceCredentials credentials = UserDataUtil.parseCredentials(args);
        AppContextCache.getContext(AppConfig.class).getBean(UserDataService.class).updateUserData(credentials);
    }
}
