package com.softserve.entity.generator.webservice;

import javax.jws.WebService;
import java.util.List;

@WebService
public class NotificationPortImpl implements NotificationPort
{
    @Override
    public boolean notifications(String organizationId, String actionId, String sessionId, String enterpriseUrl, String partnerUrl, List<EntityCNotification> notification)
    {
        System.out.println("Hello");
        return true;
    }
}
