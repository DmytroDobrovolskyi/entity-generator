trigger EntityMetadataSetter on Entity__c (after insert, after update) 
{
    NotificationMessageUtil.setNotificationMessageMetadata();
}