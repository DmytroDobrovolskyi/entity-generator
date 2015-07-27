
package com.softserve.entity.generator.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Entity__cNotification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Entity__cNotification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{urn:enterprise.soap.sforce.com}ID"/>
 *         &lt;element name="sObject" type="{urn:sobject.enterprise.soap.sforce.com}Entity__c"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Entity__cNotification", propOrder = {
    "id",
    "sObject"
})
public class EntityCNotification {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(required = true)
    protected EntityC sObject;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the sObject property.
     * 
     * @return
     *     possible object is
     *     {@link EntityC }
     *     
     */
    public EntityC getSObject() {
        return sObject;
    }

    /**
     * Sets the value of the sObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityC }
     *     
     */
    public void setSObject(EntityC value) {
        this.sObject = value;
    }

}
