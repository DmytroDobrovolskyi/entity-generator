/**
 * NotificationPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.softserve.entity.generator.webservice;

public interface NotificationPort extends java.rmi.Remote {

    /**
     * Process a number of notifications.
     */
    public boolean notifications(java.lang.String organizationId, java.lang.String actionId, java.lang.String sessionId, java.lang.String enterpriseUrl, java.lang.String partnerUrl, Entity__cNotification[] notification) throws java.rmi.RemoteException;
}