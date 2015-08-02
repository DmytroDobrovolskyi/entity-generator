public with sharing class FieldDomain extends SObjectDomain
{
    public FieldDomain(List<SObject> records)
    {
        super(records);
    }

    public class Constructor implements SObjectDomain.Constructable
    {
        public SObjectDomain construct(List<SObject> records)
        {
            return new FieldDomain(records);
        }
    }

    public override void onAfterInsert()
    {
        NotificationMessageUtil.insertMessage(records, OperationType.INSERT_OPERATION);
    }

    public override void onAfterUpdate(Map<Id, SObject> existingRecords)
    {
        NotificationMessageUtil.insertMessage(records, OperationType.INSERT_OPERATION);
    }

    public override void onAfterDelete()
    {
        NotificationMessageUtil.insertMessage(records, OperationType.DELETE_OPERATION);
    }

    public override void onBeforeInsert()
    {
        FieldUtil.generateColumnNameAndExternalId((List<Field__c>) records);
    }
}