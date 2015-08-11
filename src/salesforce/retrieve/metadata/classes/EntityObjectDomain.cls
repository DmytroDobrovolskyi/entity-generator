public with sharing class EntityObjectDomain extends SObjectDomain
{
    public EntityObjectDomain(List<SObject> records)
    {
        super(records);
    }

    public class Constructor implements SObjectDomain.Constructable
    {
        public SObjectDomain construct(List<SObject> records)
        {
            return new EntityObjectDomain(records);
        }
    }

    public override void onAfterInsert()
    {
       insertMessage(OperationType.INSERT_OPERATION);
    }

    public override void onAfterUpdate(Map<Id, SObject> existingRecords)
    {
        insertMessage(OperationType.INSERT_OPERATION);
    }

    public override void onAfterDelete()
    {
        insertMessage(OperationType.DELETE_OPERATION);
    }

    private void insertMessage(OperationType operationType)
    {
        for (Entity__c entity : (List<Entity__c>) records)
        {
            NotificationMessage__c message = new NotificationMessage__c();

            message.OperationType__c = String.valueOf(operationType);
            message.SObjectType__c = 'Entity';
            message.ExternalId__c = entity.EntityId__c;

            Database.insert(message);
        }
    }
}