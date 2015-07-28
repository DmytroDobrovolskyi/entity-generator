trigger FieldMetadataSetter on Field__c (after insert, after update, after delete)
{
    NotificationMessageUtil.setNotificationMessageMetadata();
}
