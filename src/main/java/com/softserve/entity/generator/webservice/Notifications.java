/**
 * Notifications.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.softserve.entity.generator.webservice;

public class Notifications  implements java.io.Serializable {
    private java.lang.String organizationId;

    private java.lang.String actionId;

    private java.lang.String sessionId;

    private java.lang.String enterpriseUrl;

    private java.lang.String partnerUrl;

    private Entity__cNotification[] notification;

    public Notifications() {
    }

    public Notifications(
           java.lang.String organizationId,
           java.lang.String actionId,
           java.lang.String sessionId,
           java.lang.String enterpriseUrl,
           java.lang.String partnerUrl,
           Entity__cNotification[] notification) {
           this.organizationId = organizationId;
           this.actionId = actionId;
           this.sessionId = sessionId;
           this.enterpriseUrl = enterpriseUrl;
           this.partnerUrl = partnerUrl;
           this.notification = notification;
    }


    /**
     * Gets the organizationId value for this Notifications.
     * 
     * @return organizationId
     */
    public java.lang.String getOrganizationId() {
        return organizationId;
    }


    /**
     * Sets the organizationId value for this Notifications.
     * 
     * @param organizationId
     */
    public void setOrganizationId(java.lang.String organizationId) {
        this.organizationId = organizationId;
    }


    /**
     * Gets the actionId value for this Notifications.
     * 
     * @return actionId
     */
    public java.lang.String getActionId() {
        return actionId;
    }


    /**
     * Sets the actionId value for this Notifications.
     * 
     * @param actionId
     */
    public void setActionId(java.lang.String actionId) {
        this.actionId = actionId;
    }


    /**
     * Gets the sessionId value for this Notifications.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this Notifications.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the enterpriseUrl value for this Notifications.
     * 
     * @return enterpriseUrl
     */
    public java.lang.String getEnterpriseUrl() {
        return enterpriseUrl;
    }


    /**
     * Sets the enterpriseUrl value for this Notifications.
     * 
     * @param enterpriseUrl
     */
    public void setEnterpriseUrl(java.lang.String enterpriseUrl) {
        this.enterpriseUrl = enterpriseUrl;
    }


    /**
     * Gets the partnerUrl value for this Notifications.
     * 
     * @return partnerUrl
     */
    public java.lang.String getPartnerUrl() {
        return partnerUrl;
    }


    /**
     * Sets the partnerUrl value for this Notifications.
     * 
     * @param partnerUrl
     */
    public void setPartnerUrl(java.lang.String partnerUrl) {
        this.partnerUrl = partnerUrl;
    }


    /**
     * Gets the notification value for this Notifications.
     * 
     * @return notification
     */
    public Entity__cNotification[] getNotification() {
        return notification;
    }


    /**
     * Sets the notification value for this Notifications.
     * 
     * @param notification
     */
    public void setNotification(Entity__cNotification[] notification) {
        this.notification = notification;
    }

    public Entity__cNotification getNotification(int i) {
        return this.notification[i];
    }

    public void setNotification(int i, Entity__cNotification _value) {
        this.notification[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Notifications)) return false;
        Notifications other = (Notifications) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.organizationId==null && other.getOrganizationId()==null) || 
             (this.organizationId!=null &&
              this.organizationId.equals(other.getOrganizationId()))) &&
            ((this.actionId==null && other.getActionId()==null) || 
             (this.actionId!=null &&
              this.actionId.equals(other.getActionId()))) &&
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId()))) &&
            ((this.enterpriseUrl==null && other.getEnterpriseUrl()==null) || 
             (this.enterpriseUrl!=null &&
              this.enterpriseUrl.equals(other.getEnterpriseUrl()))) &&
            ((this.partnerUrl==null && other.getPartnerUrl()==null) || 
             (this.partnerUrl!=null &&
              this.partnerUrl.equals(other.getPartnerUrl()))) &&
            ((this.notification==null && other.getNotification()==null) || 
             (this.notification!=null &&
              java.util.Arrays.equals(this.notification, other.getNotification())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getOrganizationId() != null) {
            _hashCode += getOrganizationId().hashCode();
        }
        if (getActionId() != null) {
            _hashCode += getActionId().hashCode();
        }
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        if (getEnterpriseUrl() != null) {
            _hashCode += getEnterpriseUrl().hashCode();
        }
        if (getPartnerUrl() != null) {
            _hashCode += getPartnerUrl().hashCode();
        }
        if (getNotification() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotification());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotification(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Notifications.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/2005/09/outbound", ">notifications"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organizationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/2005/09/outbound", "OrganizationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/2005/09/outbound", "ActionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/2005/09/outbound", "SessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enterpriseUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/2005/09/outbound", "EnterpriseUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partnerUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/2005/09/outbound", "PartnerUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://soap.sforce.com/2005/09/outbound", "Notification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.sforce.com/2005/09/outbound", "Entity__cNotification"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
