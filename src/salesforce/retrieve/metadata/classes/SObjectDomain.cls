public virtual with sharing class SObjectDomain
{
    protected List<SObject> records { get; private set; }

    protected String masterRelationName;

    public SObjectDomain(List<SObject> records)
    {
        this.records = records;
    }

    public virtual void onBeforeInsert() { }

    public virtual void onBeforeUpdate(Map<Id, SObject> existingRecords) { }

    public virtual void onBeforeDelete() { }

    public virtual void onAfterInsert()
    {
        NotificationMessageUtil.insertMessage(records, OperationType.INSERT_OPERATION, masterRelationName);
    }

    public virtual void onAfterUpdate(Map<Id, SObject> existingRecords)
    {
        NotificationMessageUtil.insertMessage(records, OperationType.UPDATE_OPERATION, masterRelationName);
    }

    public virtual void onAfterDelete()
    {
        NotificationMessageUtil.insertMessage(records, OperationType.DELETE_OPERATION, masterRelationName);
    }

    public static void handleTrigger(Type domainClass)
    {
        Constructable domainConstructor = (Constructable) Type.forName(domainClass.getName() + '.Constructor').newInstance();
        Map<Id, SObject> oldRecordsMap = Trigger.oldMap;
        List<SObject> newRecords = Trigger.new;

        if (Trigger.isBefore)
        {
            if (Trigger.isInsert)
            {
                domainConstructor.construct(newRecords).onBeforeInsert();
            }
            else if (Trigger.isUpdate)
            {
                domainConstructor.construct(newRecords).onBeforeUpdate(oldRecordsMap);
            }
            else if (Trigger.isDelete)
            {
                domainConstructor.construct(oldRecordsMap.values()).onBeforeDelete();
            }
        }
        else
        {
            if (Trigger.isInsert)
            {
                domainConstructor.construct(newRecords).onAfterInsert();
            }
            else if (Trigger.isUpdate)
            {
                domainConstructor.construct(newRecords).onAfterUpdate(oldRecordsMap);
            }
            else if (Trigger.isDelete)
            {
                domainConstructor.construct(oldRecordsMap.values()).onAfterDelete();
            }
        }
    }

    public interface Constructable
    {
        SObjectDomain construct(List<SObject> records);
    }
}
