public class NotificationMessageUtil 
{
	public static void setNotificationMessageMetadata()
    {
        NotificationMessage__c notificationMessage = new NotificationMessage__c();
        Map<ID, SObject> oldSObjects = Trigger.oldMap;
        for (ID newSObjectKey : Trigger.newMap.keySet())
        {
          if (oldSObjects.containsKey(newSObjectKey))
          {
              System.debug(oldSObjects.get(newSObjectKey));
              System.debug(Trigger.newMap.get(newSObjectKey));
              if (oldSObjects.get(newSObjectKey) != Trigger.newMap.get(newSObjectKey))
              {
                  System.debug('Hell yeah');
              }
          }
        }	
    }
}