public class FieldUtil 
{
	public static void generateColumnNameAndExternalId(List<Field__c>fields)
    {
		Set<Id> entityIds = new Set<Id>();
		for (Field__c field : fields)
		{
			entityIds.add(field.Entity__c);
		} 
        
		Map<Id, Entity__c> entityByName = new Map<Id, Entity__c>(
		[
			SELECT Id, Name 
			FROM Entity__c
			WHERE Id IN :entityIds
		]);
        
		for (Field__c field : fields)
		{
			Entity__c entity = entityByName.get(field.Entity__c);
			field.ColumnName__c = field.Name.replaceAll('\\s','_')
			.replaceAll('\\W','')
			.replaceAll('\\d', '')
			.toUpperCase();
            
			field.FieldId__c = entity.Name.replaceAll('\\s','')
			.replaceAll('\\W','')
			.replaceAll('\\d', '')+'.';
			field.FieldId__c += field.Name.replaceAll('\\s','')
			.replaceAll('\\W','')
			.replaceAll('\\d', '');       
		}
	}
}