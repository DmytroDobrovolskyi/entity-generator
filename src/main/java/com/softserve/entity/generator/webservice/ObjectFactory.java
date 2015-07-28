
package com.softserve.entity.generator.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.softserve.entity.generator.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NotificationMessageCOperationTypeC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OperationType__c");
    private final static QName _NotificationMessageCSObjectIdC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "SObjectId__c");
    private final static QName _NotificationMessageCSObjectTypeC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "SObjectType__c");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.softserve.entity.generator.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NotificationMessageCNotification }
     * 
     */
    public NotificationMessageCNotification createNotificationMessageCNotification() {
        return new NotificationMessageCNotification();
    }

    /**
     * Create an instance of {@link NotificationsResponse }
     * 
     */
    public NotificationsResponse createNotificationsResponse() {
        return new NotificationsResponse();
    }

    /**
     * Create an instance of {@link NotificationMessageC }
     * 
     */
    public NotificationMessageC createNotificationMessageC() {
        return new NotificationMessageC();
    }

    /**
     * Create an instance of {@link AggregateResult }
     * 
     */
    public AggregateResult createAggregateResult() {
        return new AggregateResult();
    }

    /**
     * Create an instance of {@link SObject }
     * 
     */
    public SObject createSObject() {
        return new SObject();
    }

    /**
     * Create an instance of {@link Notifications }
     * 
     */
    public Notifications createNotifications() {
        return new Notifications();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OperationType__c", scope = NotificationMessageC.class)
    public JAXBElement<String> createNotificationMessageCOperationTypeC(String value) {
        return new JAXBElement<String>(_NotificationMessageCOperationTypeC_QNAME, String.class, NotificationMessageC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "SObjectId__c", scope = NotificationMessageC.class)
    public JAXBElement<String> createNotificationMessageCSObjectIdC(String value) {
        return new JAXBElement<String>(_NotificationMessageCSObjectIdC_QNAME, String.class, NotificationMessageC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "SObjectType__c", scope = NotificationMessageC.class)
    public JAXBElement<String> createNotificationMessageCSObjectTypeC(String value) {
        return new JAXBElement<String>(_NotificationMessageCSObjectTypeC_QNAME, String.class, NotificationMessageC.class, value);
    }

}
