package com.softserve.entity.generator.webservice;

import com.sforce.soap._2005._09.outbound.NotificationMessageCNotification;
import com.sforce.soap._2005._09.outbound.NotificationPort;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.config.util.AppContextCache;
import com.softserve.entity.generator.entity.DatabaseObject;
import com.softserve.entity.generator.salesforce.FetchType;
import com.softserve.entity.generator.salesforce.SObjectProcessor;
import com.softserve.entity.generator.salesforce.SObjectSynchronizer;
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
        Map<Class<T>, Map<OperationType, List<String>>> classMap = new HashMap<Class<T>, Map<OperationType, List<String>>>();

        List<Class<T>> classList = new ArrayList<Class<T>>();

        for (NotificationMessageCNotification notificationMessage : notifications)
        {
            Class<T> javaClassAnalogue = ParsingUtil.<T>toJavaClass(notificationMessage.getSObject().getSObjectTypeC().getValue());
            OperationType operationType = OperationType.valueOf(notificationMessage.getSObject().getOperationTypeC().getValue());
            String objectId = notificationMessage.getSObject().getExternalIdC().getValue();

            Map<OperationType, List<String>> operationMap = classMap.get(javaClassAnalogue);
            if (operationMap == null)
            {
                operationMap = new HashMap<OperationType, List<String>>();

                operationMap.put(OperationType.INSERT_OPERATION, new ArrayList<String>());
                operationMap.put(OperationType.UPDATE_OPERATION, new ArrayList<String>());
                operationMap.put(OperationType.DELETE_OPERATION, new ArrayList<String>());

                classMap.put(javaClassAnalogue, operationMap);
                classList.add(javaClassAnalogue);
            }
            operationMap.get(operationType).add(objectId);
            System.out.println(classMap);
        }

        for (Class<T> objectClass : classList)
        {
            syncData(classMap.get(objectClass), objectClass, sessionId);
        }

        return true;
    }

    private void syncData(Map<OperationType, List<String>> operationMap, Class<T> objectClass, String sessionId)
    {
        for (Map.Entry<OperationType, List<String>> operationEntry : operationMap.entrySet())
        {
            OperationType operation = operationEntry.getKey();
            List<String> idList = operationEntry.getValue();

            if (!idList.isEmpty())
            {
                switch (operation)
                {
                    case INSERT_OPERATION:
                    case UPDATE_OPERATION:
                        syncOnInsertUpdate(idList, objectClass, sessionId);
                        break;
                    case DELETE_OPERATION:
                        break;
                }
            }
        }
    }

    private void syncOnInsertUpdate(List<String> idList, Class<T> objectClass, String sessionId)
    {
        @SuppressWarnings("unchecked")
        BatchService<DatabaseObject> batchService = AppContextCache.getContext(AppConfig.class).getBean(BatchService.class);

        List<T> newObjects = SObjectProcessor.getInstance(sessionId, objectClass)
                .getAll(objectClass.getSimpleName() + "Id__c", idList, FetchType.LAZY);

        System.out.println(newObjects);

        List<DatabaseObject> synchronizedObjects = SObjectSynchronizer.syncObjects(toIdMap(idList, newObjects), OperationType.UPDATE_OPERATION);

        batchService.batchMerge(synchronizedObjects);
    }

    private Map<String, T> toIdMap(List<String> idList, List<T> objectList)
    {
        int objectListSize = objectList.size();
        int idListSize = idList.size();

        if (objectListSize != idListSize)
        {
            throw new AssertionError("Could not convert to map: idList size=" + idListSize + " objectList size=" + objectListSize);
        }

        Map<String, T> resultMap = new HashMap<String, T>();
        for (int i = 0; i < objectListSize; i++)
        {
            resultMap.put(idList.get(i), objectList.get(i));
        }

        return resultMap;
    }
}
