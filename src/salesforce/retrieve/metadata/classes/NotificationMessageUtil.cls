public class NotificationMessageUtil
{
	public static void setNotificationMessageMetadata()
    {
        if (Trigger.isDelete)
        {
            processSObjects(Trigger.old, OperationType.DELETE_OPERATION);
        }
        else
        {
            processSObjects(Trigger.new, Trigger.isInsert ? OperationType.INSERT_OPERATION : OperationType.UPDATE_OPERATION);
        }
    }
    private static void processSObjects(List<SObject> sObjectsToProcess, OperationType operationType)
    {
        for (SObject sObjectToProcess : sObjectsToProcess)
        {
            NotificationMessage__c notificationMessage = new NotificationMessage__c();

            notificationMessage.OperationType__c = operationType.name();
            notificationMessage.ExternalId__c = ((Entity__c) sObjectToProcess).EntityId__c;
            notificationMessage.SObjectType__c = String.valueOf(sObjectsToProcess.getSObjectType());
            Database.insert(notificationMessage);
        }
    }
}
