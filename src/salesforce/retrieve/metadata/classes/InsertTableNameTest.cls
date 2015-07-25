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
}