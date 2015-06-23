trigger InsertTableName on Entity__c(before insert)
{
    for(Entity__c e : Trigger.new)
    {
            e.TableName__c = e.Name.replaceAll('\\s','_')
                    .replaceAll('\\W','')
                    .replaceAll('\\d', '')
                    .toUpperCase();
    }
}