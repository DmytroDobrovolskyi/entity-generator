public class NotificationMessageUtil 
{
	public static void setNotificationMessageMetadata()
    {
        if (Trigger.isDelete)
        {
            processSObjects(Trigger.old, 'delete');
        }
        else
        {
            String operationType = Trigger.isInsert ? 'insert' : 'update';
            processSObjects(Trigger.new, operationType);
        }
    }

    private static void processSObjects(List<SObject> sObjectsToProcess, String operationType)
    {
        for (SObject sObjectToProcess : sObjectsToProcess)
        {
            NotificationMessage__c notificationMessage = new NotificationMessage__c();

            notificationMessage.OperationType__c = operationType;

            String type = String.valueOf(sObjectToProcess.getSObjectType());
            if (type == 'Entity__c')
            {
                notificationMessage.SObjectId__c = ((Entity__c) sObjectToProcess).Id;
            }
            else
            {
                notificationMessage.SObjectId__c = ((Field__c) sObjectToProcess).Id;
            }

            notificationMessage.SObjectType__c = type;

            Database.insert(notificationMessage);
        }
    }
}
