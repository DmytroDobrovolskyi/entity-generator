trigger NotificationMessageSetter on Entity__c (after insert, after update) 
{
    NotificationMessageUtil.setNotificationMessageMetadata();
}