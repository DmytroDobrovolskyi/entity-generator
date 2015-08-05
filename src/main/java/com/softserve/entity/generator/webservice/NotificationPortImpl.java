package com.softserve.entity.generator.webservice;

import com.sforce.soap._2005._09.outbound.NotificationMessageCNotification;
import com.sforce.soap._2005._09.outbound.NotificationPort;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.salesforce.*;
import com.softserve.entity.generator.salesforce.util.ObjectType;
import com.softserve.entity.generator.salesforce.util.ParsingUtil;
import com.softserve.entity.generator.service.BatchService;
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
            syncData(classToOperationMap.get(objectClass), objectClass, sessionId);
        }

        return true;
    }

    private void syncData(Map<OperationType, List<String>> operationToIds, Class<T> objectClass, String sessionId)
    {
        for (Map.Entry<OperationType, List<String>> operationEntry : operationToIds.entrySet())
        {
            OperationType operation = operationEntry.getKey();
            List<String> idList = operationEntry.getValue();

            if (!idList.isEmpty())
            {
                switch (operation)
                {
                    case INSERT_OPERATION:
                        break;
                    case UPDATE_OPERATION:
                        syncOnUpdate(idList, objectClass, sessionId);
                        break;
                    case DELETE_OPERATION:
                        break;
                }
            }
        }
    }

    private void syncOnUpdate(List<String> ids, Class<T> objectClass, String sessionId)
    {
        ObjectType objectType = SObjectRegister.getSObjectMetadata(objectClass).getObjectType();
        SObjectProcessor<T> objectProcessor = SObjectProcessor.getInstance(sessionId, objectClass);
        String condition = objectClass.getSimpleName() + "Id__c";
        List<T> newStateObjects = new ArrayList<T>();
        switch (objectType)
        {
            case HIGH_ORDER_OBJECT:
                newStateObjects.addAll(objectProcessor.getAll(condition, ids, FetchType.LAZY));
                break;
            case SUBORDER_OBJECT:
                newStateObjects.addAll(objectProcessor.getAll(condition, ids, FetchType.EAGER));
        }
        List<DatabaseObject> resolvedObjects =  SObjectStateResolver.resolveOnUpdate(getIdToObject(ids, newStateObjects), objectClass);

        @SuppressWarnings("unchecked")
        BatchService<DatabaseObject> batchService = AppContextCache.getContext(AppConfig.class).getBean(BatchService.class);

        batchService.batchMerge(resolvedObjects);
    }

    private Map<String, T> getIdToObject(List<String> ids, List<T> objects)
    {
        int objectsQuantity = objects.size();
        int idsQuantity = ids.size();

        if (objectsQuantity != idsQuantity)
        {
            throw new AssertionError("Could not convert to map: ids size=" + idsQuantity + " objects size=" + objectsQuantity);
        }

        Map<String, T> resultMap = new HashMap<String, T>();
        for (int i = 0; i < objectsQuantity; i++)
        {
            resultMap.put(ids.get(i), objects.get(i));
        }

        return resultMap;
    }
}
