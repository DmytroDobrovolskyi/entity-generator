package com.softserve.entity.generator.webservice;


import com.softserve.entity.generator.app.EntityGenerator;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.UserDataService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/")
public class EntityGeneratorWebService
{
    private static final Logger logger = Logger.getLogger(EntityGeneratorWebService.class);

    private static final String SELECT_STATE =
            "RequestState__c requestState = "  +
            "[" +
                "SELECT Status__c " +
                "FROM RequestState__c LIMIT 1" +
            "]; ";
    private static final String UPDATE_STATE = "Database.update(requestState);";

    @POST
    @Path("generate-entities")
    public void generateTables(String requestBody)
    {
        ApplicationContext context = AppContextCache.getContext(AppConfig.class);
        SalesforceCredentials credentials = context.getBean(UserDataService.class).findByOrganizationId(requestBody);

        WebServiceUtil webServiceUtil = WebServiceUtil.getInstance(credentials);
        webServiceUtil.executeApex(
                SELECT_STATE + "requestState = '" + RequestStatus.IN_PROGRESS + "'; " + UPDATE_STATE
        );
        try
        {
            EntityService entityService = context.getBean(EntityService.class);
            entityService.applyData();

            EntityGenerator.generate(credentials);
            WebServiceUtil.getInstance(credentials).executeApex(
                    SELECT_STATE + "requestState = '" + RequestStatus.COMPLETED + "'; " + UPDATE_STATE
            );
        }
        catch (Throwable ex)
        {
            logger.error("Failed to generate entities: ", ex);
            WebServiceUtil.getInstance(credentials).executeApex(
                    SELECT_STATE + "requestState = '" + RequestStatus.FAILED + "'; " + UPDATE_STATE
            );
        }
    }
}
