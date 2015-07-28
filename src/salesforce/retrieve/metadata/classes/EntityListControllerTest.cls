@isTest
public class EntityListControllerTest 
{  
    static testMethod void containsNumbersOrNonAlphabeticCharactersTest()
    {
        test.startTest();
        
        EntityListController entityController = new EntityListController();
        String testString = 'test1';
        
        test.stopTest();
        
        System.assert(entityController.containsNumbersOrNonAlphabeticCharacters(testString));
    }
    
    static testMethod void getEntitiesTest()
    {
        test.startTest();
        
        Entity__c entityTest = new Entity__c();
        entityTest.TableName__c = 'entityTableNameTest';
        entityTest.Name = 'entityNameTest';
        
        Database.insert(entityTest);
        
	    EntityListController entityController = new EntityListController();
    	Integer size = entityController.getEntities().size();
        Integer expectedEntitiesListSize = 1;
        
        test.stopTest();
        
        System.assertEquals(expectedEntitiesListSize,size);
    }
    
    static testMethod void wereChangesTest()
    {
        test.startTest();

        EntityListController entityController = new EntityListController();
        Integer size = entityController.entityMap.size();
        entityController.addRow();
        
        test.stopTest();
        
        System.assert(entityController.wereChanges);
        System.assertEquals(size+1,entityController.entityMap.size());
    }

    static testMethod void applyChangesTest()
    {
         test.startTest();

         Entity__c entityTest = new Entity__c();
         entityTest.TableName__c = 'entityTableNameTest';
         entityTest.Name = 'entityNameTest';

         Database.insert(entityTest);

    	 EntityListController entityController = new EntityListController();
    	 entityController.applyChanges();

    	 System.assertEquals(entityController.entityMap.size(),entityController.newEntityMap.size());
         test.stopTest();
    }
}