package com.softserve.entity.generator.webservice.impl;

import com.softserve.entity.generator.app.util.LoginUtil;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.salesforce.SObjectProcessor;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.webservice.NotificationMessageCNotification;
import com.softserve.entity.generator.webservice.NotificationPort;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.ArrayList;
import java.util.List;

@WebService(name = "NotificationPort", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
public class NotificationPortImpl implements NotificationPort
{
    private static final Logger logger = Logger.getLogger(NotificationPortImpl.class);
    private static final String TARGET_NAMESPACE = "http://soap.sforce.com/2005/09/outbound";

    @WebMethod
    @WebResult(name = "Ack", targetNamespace = TARGET_NAMESPACE)
    @RequestWrapper(localName = "notifications", targetNamespace = TARGET_NAMESPACE, className = "com.softserve.entity.generator.webservice.Notifications")
    @ResponseWrapper(localName = TARGET_NAMESPACE, targetNamespace = TARGET_NAMESPACE, className = "com.softserve.entity.generator.webservice.NotificationsResponse")
    public boolean notifications(
            @WebParam(name = "OrganizationId", targetNamespace = TARGET_NAMESPACE)
            String organizationId,
            @WebParam(name = "ActionId", targetNamespace = TARGET_NAMESPACE)
            String actionId,
            @WebParam(name = "SessionId", targetNamespace = TARGET_NAMESPACE)
            String sessionId,
            @WebParam(name = "EnterpriseUrl", targetNamespace = TARGET_NAMESPACE)
            String enterpriseUrl,
            @WebParam(name = "PartnerUrl", targetNamespace = TARGET_NAMESPACE)
            String partnerUrl,
            @WebParam(name = "Notification", targetNamespace = TARGET_NAMESPACE)
            List<NotificationMessageCNotification> notifications)
    {
        List<Entity> entitiesToSync = new ArrayList<Entity>();
        SObjectProcessor<Entity> objectProcessor = new SObjectProcessor<Entity>(LoginUtil.getPersistedCredentials(), Entity.class);

        for (NotificationMessageCNotification notificationMessage : notifications)
        {
            String sObjectId = notificationMessage.getSObject().getSalesforceIdC().getValue();
            entitiesToSync.add(
                    objectProcessor.getBySalesforceId(sObjectId)
            );
        }

        return true;
    }

    private void syncData(List<Entity> entitiesToSync)
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        EntityService entityService = context.getBean(EntityService.class);
        entityService.batchMerge(entitiesToSync);
    }
}
