trigger InsertTableName on Entity__c(before insert)
{
    List<Entity__c>entities = Trigger.new;
    EntityUtil.generateEntityTable(entities);
}
