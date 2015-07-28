
package com.softserve.entity.generator.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotificationMessage__c complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificationMessage__c">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:sobject.enterprise.soap.sforce.com}sObject">
 *       &lt;sequence>
 *         &lt;element name="OperationType__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SObjectId__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SObjectType__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationMessage__c", namespace = "urn:sobject.enterprise.soap.sforce.com", propOrder = {
    "operationTypeC",
    "sObjectIdC",
    "sObjectTypeC"
})
public class NotificationMessageC
    extends SObject
{

    @XmlElementRef(name = "OperationType__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> operationTypeC;
    @XmlElementRef(name = "SObjectId__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> sObjectIdC;
    @XmlElementRef(name = "SObjectType__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> sObjectTypeC;

    /**
     * Gets the value of the operationTypeC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOperationTypeC() {
        return operationTypeC;
    }

    /**
     * Sets the value of the operationTypeC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOperationTypeC(JAXBElement<String> value) {
        this.operationTypeC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sObjectIdC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSObjectIdC() {
        return sObjectIdC;
    }

    /**
     * Sets the value of the sObjectIdC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSObjectIdC(JAXBElement<String> value) {
        this.sObjectIdC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sObjectTypeC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSObjectTypeC() {
        return sObjectTypeC;
    }

    /**
     * Sets the value of the sObjectTypeC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSObjectTypeC(JAXBElement<String> value) {
        this.sObjectTypeC = ((JAXBElement<String> ) value);
    }

}
