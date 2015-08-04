package com.softserve.entity.generator.webservice;

import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.salesforce.SalesforceCredentials;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.service.EntityService;



import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class TablesGenerator
{
    private static final String RESET_IS_PROCESSING_NEEDED = "EntityUtil.resetIsProcessingNeeded();";

    @POST
    @Path("generate-tables")
    public Response generateTables() {

        String output = "success : ";
        System.out.println("hello");
        generateEntities();
        System.out.println("ok");

        return Response.status(200).entity(output).build();
    }

    private void generateEntities()
    {
        EntityService entityService = AppContextCache.getContext(AppConfig.class).getBean(EntityService.class);
        entityService.applyData();

        WebServiceUtil.getInstance(new SalesforceCredentials("oles1719@gmail.com","kmdsd564","9DXG5kJRlcLyRnWX2rIMbe8h9")).executeApex(RESET_IS_PROCESSING_NEEDED);
    }
}
