global class EntityUtil
{
    @TestVisible
    private static List<Entity__c> entityList { get; set; }

    public static void generateEntityTable(List<Entity__c> entities)
    {
        for(Entity__c entity : entities)
        {
            String tableName = entity.TableName__c
                    .replaceAll('\\s','')
                    .replaceAll('\\W','')
                    .replaceAll('_','')
                    .replaceAll('\\d', '');

            entity.TableName__c = entity.TableName__c
                    .replaceAll('\\s','_')
                    .replaceAll('\\W','')
                    .replaceAll('\\d', '')
                    .toUpperCase();

            entity.EntityId__c = tableName.toLowerCase();
        }
    }

    public static void resetIsProcessingNeeded()
    {
        entityList =
        [
            SELECT IsProcessingNeeded__c, Name
            FROM Entity__c
        ];

        for (Entity__c entity : entityList)
        {
            entity.IsProcessingNeeded__c = false;
        }
        Database.update(entityList);
    }
}