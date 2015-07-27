package com.softserve.entity.generator.webservice;

import javax.jws.WebService;
import java.util.List;

@WebService(name = "NotificationPortImpl", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
public class NotificationPortImpl implements NotificationPort
{
    @Override
    public boolean notifications(String organizationId, String actionId, String sessionId, String enterpriseUrl, String partnerUrl, List<EntityCNotification> notification)
    {
        System.out.println(notification.get(0).getSObject().getId());
        System.out.println("notifications invoked");
        return true;
    }
}
