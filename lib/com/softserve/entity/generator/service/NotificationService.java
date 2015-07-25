/**
 * NotificationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.softserve.entity.generator.service;

public interface NotificationService extends javax.xml.rpc.Service {

/**
 * Notification Service Implementation
 */
    public java.lang.String getNotificationAddress();

    public com.softserve.entity.generator.service.NotificationPort getNotification() throws javax.xml.rpc.ServiceException;

    public com.softserve.entity.generator.service.NotificationPort getNotification(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
