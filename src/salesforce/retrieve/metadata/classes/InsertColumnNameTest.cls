@isTest
public class InsertColumnNameTest 
{
 static testMethod void testColumnNameTrigger()
    {      
        test.startTest();
       
        Entity__c entityTest = new Entity__c();
        entityTest.TableName__c = 'entityTableNameTest';
        entityTest.Name = 'entityNameTest';
        
        Database.insert(entityTest);
        
        Field__c fieldTest = new Field__c();
        fieldTest.ColumnName__c = 'fieldColumnName';
        fieldTest.Name = 'fieldTest';
        fieldTest.Type__c = 'int';
        fieldTest.IsPrimaryKey__c = false;
        fieldTest.Entity__c = entityTest.Id;
        
        Database.SaveResult result = Database.insert(fieldTest);  
        
        test.stopTest();
        
        System.assert(result.isSuccess());          
    }
}
