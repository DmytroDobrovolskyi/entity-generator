package com.softserve.entity.generator.webservice;


import com.sforce.ws.ConnectionException;
import com.softserve.entity.generator.app.EntityGenerator;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.service.UserDataService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class TablesGenerator
{
    private static final Logger logger = Logger.getLogger(TablesGenerator.class);
    private static final String SUCCESSFUL_MESSAGE = "successful request";
    private static final String FAILED_MESSAGE = "request was not successful";

    @POST
    @Path("generate-tables")
    public Response generateTables(String requestBody)
    {
        try
        {
            generateEntities(requestBody.replaceAll("name=", ""));
            return Response.status(200).entity(SUCCESSFUL_MESSAGE).build();
        }
        catch (ConnectionException ex) {
            logger.error("Failed to log in. Check your credentials and internet connection");
        }
        return Response.status(500).entity(FAILED_MESSAGE).build();
    }

    private void generateEntities(String organizationId) throws ConnectionException
    {
        ApplicationContext context = AppContextCache.getContext(AppConfig.class);
        EntityService entityService = context.getBean(EntityService.class);
        entityService.applyData();

        SalesforceCredentials credentials = context.getBean(UserDataService.class).findByOrganizationId(organizationId);
        EntityGenerator.generate(credentials);
    }
}
