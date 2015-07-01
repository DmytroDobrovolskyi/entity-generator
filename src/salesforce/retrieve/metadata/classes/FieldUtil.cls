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

			String columnName = field.ColumnName__c
													.replaceAll('\\s', '')
													.replaceAll('\\W', '')
													.replaceAll('_', '')
													.replaceAll('\\d', '');

			field.ColumnName__c = field.ColumnName__c
					.replaceAll('\\s','_')
					.replaceAll('\\W','')
					.replaceAll('\\d', '')
					.toUpperCase();
            
			field.FieldId__c = entity.TableName__c
					.replaceAll('\\s','')
					.replaceAll('\\W','')
					.replaceAll('\\d', '')+'.';

			field.FieldId__c += columnName;
		}
	}
}