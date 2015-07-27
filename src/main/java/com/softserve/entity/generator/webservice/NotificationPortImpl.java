package com.softserve.entity.generator.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@WebService(name = "NotificationPortImpl", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
public class NotificationPortImpl implements NotificationPort
{
    @Override
    @WebMethod
    @WebResult(name = "Ack", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
    @RequestWrapper(localName = "notifications", targetNamespace = "http://soap.sforce.com/2005/09/outbound", className = "com.softserve.entity.generator.webservice.Notifications")
    @ResponseWrapper(localName = "notificationsResponse", targetNamespace = "http://soap.sforce.com/2005/09/outbound", className = "com.softserve.entity.generator.webservice.NotificationsResponse")
    public boolean notifications(
            @WebParam(name = "OrganizationId", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
            String organizationId,
            @WebParam(name = "ActionId", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
            String actionId,
            @WebParam(name = "SessionId", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
            String sessionId,
            @WebParam(name = "EnterpriseUrl", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
            String enterpriseUrl,
            @WebParam(name = "PartnerUrl", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
            String partnerUrl,
            @WebParam(name = "Notification", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
            List<EntityCNotification> notification)
    {
        System.out.println("Hello");
        return true;
    }
}
