public class NotificationMessageUtil
{
	public static void insertMessage(List<SObject> sObjects, OperationType operationType)
    {
        for (SObject sObjectToProcess : sObjects)
        {
            NotificationMessage__c message = new NotificationMessage__c();

            String sObjectType = String.valueOf(sObjectToProcess.getSObjectType());

            message.SObjectType__c = sObjectType;
            message.ExternalId__c = String.valueOf(sObjectToProcess.get(toExternalIdFieldName(sObjectType)));
            message.OperationType__c = String.valueOf(operationType);

            Database.insert(message);
        }
    }

    private static String toExternalIdFieldName(String sObjectType)
    {
        return sObjectType.replace('__c', 'Id__c');
    }
}
