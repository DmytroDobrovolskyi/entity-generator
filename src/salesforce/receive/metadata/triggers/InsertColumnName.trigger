trigger InsertColumnName on Field__c(before insert)
{
    Set<Id> entityIds = new Set<Id>();
    for (Field__c field : Trigger.new)
    {
        entityIds.add(field.Entity__c);
    }
    
    Map<Id, Entity__c> entityByName = new Map<Id, Entity__c>(
    [
        SELECT Id, Name 
        FROM Entity__c
        WHERE Id IN :entityIds
    ]);
    for (Field__c field : Trigger.new)
    {
        Entity__c entity = entityByName.get(field.Entity__c);
        field.ColumnName__c = entity.Name +'.';
        field.ColumnName__c += field.Name.replaceAll('\\s','_')
                .replaceAll('\\W','')
                .replaceAll('\\d', '')
                .toUpperCase();
    }
}