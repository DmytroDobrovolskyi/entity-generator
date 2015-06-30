public class EntityUtil 
{
    public static void generateEntityTable(List<Entity__c>entities)
    {
       for(Entity__c entity : entities)
   		{
       	  entity.TableName__c = entity.Name.replaceAll('\\s','_')
                    .replaceAll('\\W','')
                    .replaceAll('\\d', '')
                    .toUpperCase();
            entity.EntityId__c = entity.Name.replaceAll('\\s','')
                    .replaceAll('\\W','')
                    .replaceAll('\\d', '');
    	}
    }
}