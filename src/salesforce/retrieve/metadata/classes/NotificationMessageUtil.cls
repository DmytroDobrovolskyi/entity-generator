public class NotificationMessageUtil 
{
	public static void setNotificationMessageMetadata()
    {
        for (SObject sObjectToProcess : Trigger.new)
        {
            NotificationMessage__c notificationMessage = new NotificationMessage__c();
            String sObjectType = String.valueOf(sObjectToProcess.getSObjectType());
            notificationMessage.SObjectType__c = sObjectType;
            notificationMessage.ExternalId__c = 'test';
            notificationMessage.OperationType__c  = 'insert';
            Database.insert(notificationMessage);
        }
    }
}
