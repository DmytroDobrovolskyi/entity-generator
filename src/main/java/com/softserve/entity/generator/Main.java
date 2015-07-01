package com.softserve.entity.generator;


import com.softserve.entity.generator.config.JpaConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.Applier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static com.softserve.entity.generator.entity.util.EntityGenerator.generateEntity;

@Component
public class Main
{
/*    private EnterpriseConnection connection;
    private ConnectorConfig config;
    private LoginResult loginResult;
    private static final Logger logger = Logger.getLogger(Main.class);*/

    @Autowired
    private Applier<Entity> applier;

    @Autowired
    private EntityService entityService;

    public static void main(String[] args)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        Main main = context.getBean(Main.class);
        main.testConfig();
        main.testApplier();
        main.login();
    }

    public void testConfig()
    {
        entityService.merge(new Entity("Any", "Any"));
    }

    public void testApplier()
    {
        applier.apply(generateEntity());
    }

    public void login(){
       /* config = new ConnectorConfig();
        config.setUsername(Config.getConfig().getUserName());
        config.setPassword(Config.getConfig().getPassword());
        try
        {
            connection = Connector.newConnection(config);
            loginResult = connection.login(Config.getConfig().getUserName(), Config.getConfig().getPassword());
            System.out.println(loginResult.getSessionId());
        }
        catch (ConnectionException e1)
        {
            System.out.println("fail to login");
        }*/
    }
}
