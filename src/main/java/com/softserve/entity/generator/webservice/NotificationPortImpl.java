package com.softserve.entity.generator.webservice;

import com.sforce.soap._2005._09.outbound.NotificationMessageCNotification;
import com.sforce.soap._2005._09.outbound.NotificationPort;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.entity.operations.SalesforceCredentials;
import com.softserve.entity.generator.salesforce.SObjectSynchronizer;
import com.softserve.entity.generator.salesforce.WebServiceUtil;
import com.softserve.entity.generator.salesforce.util.ParsingUtil;
import com.softserve.entity.generator.service.UserDataService;
import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebService(name = "NotificationPort", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
public class NotificationPortImpl<T extends DatabaseObject> implements NotificationPort
{
    private static final Logger logger = Logger.getLogger(NotificationPortImpl.class);
    private static final String TARGET_NAMESPACE = "http://soap.sforce.com/2005/09/outbound";

    @WebMethod
    @WebResult(name = "Ack", targetNamespace = TARGET_NAMESPACE)
    @RequestWrapper(localName = "notifications", targetNamespace = TARGET_NAMESPACE, className = "com.softserve.entity.generator.webservice.Notifications")
    @ResponseWrapper(localName = "notificationsResponse", targetNamespace = TARGET_NAMESPACE, className = "com.softserve.entity.generator.webservice.NotificationsResponse")
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
        Map<Class<T>, Map<OperationType, List<String>>> classToOperationMap = new HashMap<Class<T>, Map<OperationType, List<String>>>();

        List<Class<T>> classList = new ArrayList<Class<T>>();

        for (NotificationMessageCNotification notificationMessage : notifications)
        {
            Class<T> javaClassAnalogue = ParsingUtil.<T>toJavaClass(notificationMessage.getSObject().getSObjectTypeC().getValue());
            OperationType operationType = OperationType.valueOf(notificationMessage.getSObject().getOperationTypeC().getValue());
            String objectId = notificationMessage.getSObject().getExternalIdC().getValue();

            Map<OperationType, List<String>> operationToIds = classToOperationMap.get(javaClassAnalogue);
            if (operationToIds == null)
            {
                operationToIds = new HashMap<OperationType, List<String>>();

                operationToIds.put(OperationType.INSERT_OPERATION, new ArrayList<String>());
                operationToIds.put(OperationType.UPDATE_OPERATION, new ArrayList<String>());
                operationToIds.put(OperationType.DELETE_OPERATION, new ArrayList<String>());

                classToOperationMap.put(javaClassAnalogue, operationToIds);
                classList.add(javaClassAnalogue);
            }
            operationToIds.get(operationType).add(objectId);
        }

        for (Class<T> objectClass : classList)
        {
            syncObjects(classToOperationMap.get(objectClass), objectClass, sessionId);
        }

        SalesforceCredentials credentials = AppContextCache.getContext(AppConfig.class).getBean(UserDataService.class)
                .findByOrganizationId(organizationId);

        WebServiceUtil.getInstance(credentials).executeApex("System.debug('Test');");

        return true;
    }

    private void syncObjects(Map<OperationType, List<String>> operationToIds, Class<T> objectClass, String sessionId)
    {
        for (Map.Entry<OperationType, List<String>> operationToIdEntry : operationToIds.entrySet())
        {
            List<String> idList = operationToIdEntry.getValue();
            if (!idList.isEmpty())
            {
                SObjectSynchronizer.sync(idList, operationToIdEntry.getKey(), objectClass, sessionId);
            }
        }
    }
}
