public with sharing class EntityDomain extends SObjectDomain
{
    public EntityDomain(List<SObject> records)
    {
        super(records);
    }

    public class Constructor implements SObjectDomain.Constructable
    {
        public SObjectDomain construct(List<SObject> records)
        {
            return new EntityDomain(records);
        }
    }

    public override void onBeforeInsert()
    {
        EntityUtil.generateEntityTableName((List<Entity__c>) records);
    }
}
