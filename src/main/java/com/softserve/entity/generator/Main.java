package com.softserve.entity.generator;

import com.softserve.entity.generator.config.JpaConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.applier.Applier;
import com.softserve.entity.generator.service.request.Authentication;
import com.softserve.entity.generator.service.request.EntityRequester;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


import static com.softserve.entity.generator.entity.util.EntityGenerator.generateEntity;

@Component
public class Main
{

    private static final Logger logger = Logger.getLogger(Main.class);

    @Autowired
    private Applier<Entity> applier;

    @Autowired
    private EntityService entityService;

    public static void main(String[] args)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        Main main = context.getBean(Main.class);
        main.testConfig();
       /* main.testApplier();*/
        EntityRequester entityRequester = new EntityRequester();
        entityRequester.getAllEntitiesWithFields();
      /*  entityRequester.getFullEntityInfo();*/
        /*entityRequester.getEntityByExternalId("ENTITY");*/
/*        entityRequester.getById("a002000000fIb4LAAS");*/


       /* Options options = new Options();
        options.addOption("t", false, "display current time");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            if(cmd.hasOption("t")) {
                System.out.println("print the date and time");
            }
            else {
                System.out.println("print the date");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


// add t option
        /*options.addOption("t", false, "display current time");*/
    }

    public void testConfig()
    {
        entityService.merge(new Entity("Any", "Any"));
    }

    public void testApplier()
    {
        applier.apply(generateEntity());
    }

}
