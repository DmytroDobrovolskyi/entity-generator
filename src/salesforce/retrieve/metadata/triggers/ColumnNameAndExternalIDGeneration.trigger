trigger ColumnNameAndExternalIDGeneration on Field__c(before insert)
{
List<Field__c>fields = Trigger.new;
FieldUtil.generateColumnNameAndExternalId(fields);             
}