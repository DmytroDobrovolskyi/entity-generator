trigger FieldMetadataSetter on Field__c (after insert, after update) 
{
    NotificationMessageUtil.setNotificationMessageMetadata();
}