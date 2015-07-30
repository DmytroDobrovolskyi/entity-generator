trigger EntityMetadataSetter on Entity__c (after insert, after update, after delete)
{
    NotificationMessageUtil.setNotificationMessageMetadata();
}
