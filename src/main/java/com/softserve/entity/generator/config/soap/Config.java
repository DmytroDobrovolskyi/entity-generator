package com.softserve.entity.generator.config.soap;

public class Config {
    private static Config config;
    private String userName;
    private String password;

    private Config(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }

    public static Config getConfig()
    {
        if (config == null) {
            config = new Config("oles1719@gmail.com", "kmdsd5642fWdaejIX1FBZNDWx1Wlw42QM");
        }
        return config;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
