public with sharing class FieldDomain extends SObjectDomain
{
    public FieldDomain(List<SObject> records)
    {
        super(records);
        masterRelationName = 'Entity__r';
    }

    public class Constructor implements SObjectDomain.Constructable
    {
        public SObjectDomain construct(List<SObject> records)
        {
            return new FieldDomain(records);
        }
    }

    public override void onBeforeInsert()
    {
        FieldUtil.generateColumnNameAndExternalId((List<Field__c>) records);
    }
}
