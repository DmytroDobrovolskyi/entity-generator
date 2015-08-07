package com.softserve.entity.generator.app.operations;

import com.softserve.entity.generator.app.util.UserDataUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.service.UserDataService;

public class DeleteUser
{
    public static void main(String[] args)
    {
        String username = UserDataUtil.parseUsername(args);
        AppContextCache.getContext(AppConfig.class).getBean(UserDataService.class).deleteUser(username);
    }
}
