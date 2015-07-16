import com.sforce.soap.enterprise.sobject.Entity__c;

import java.util.List;

public class EntityUtil
{
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
        for (Entity__c :
        [
            SELECT
        ])
    }
}