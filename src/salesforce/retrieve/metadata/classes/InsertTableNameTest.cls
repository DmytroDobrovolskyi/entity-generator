@isTest
public class InsertTableNameTest
{
    static testMethod void testTableNameTrigger()
    {
        Entity__c entity = new Entity__c();
        entity.TableName__c = 'entityTestTableName';
        entity.Name = 'testName';

        test.startTest();

        Database.SaveResult result = Database.insert(entity);

        test.stopTest();

        System.assert(result.isSuccess());
    }

    static testMethod void resetIsProcessingNeededTest()
    {
        Entity__c entityTest = new Entity__c();
        entityTest.TableName__c = 'entityTestTableName';
        entityTest.Name = 'testName';

        test.startTest();

        Database.insert(entityTest);

        EntityUtil.resetIsProcessingNeeded();

        test.stopTest();
        for(Entity__c entity : EntityUtil.entityList)
        {
            System.assert(!entity.IsProcessingNeeded__c);
        }
    }
}