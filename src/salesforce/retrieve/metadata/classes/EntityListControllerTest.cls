@isTest
public class EntityListControllerTest
{
    static 
    {
        insertCustomSettingObject();
    }
    
 	static testMethod void containsNumbersOrNonAlphabeticCharactersTest()
    {
        test.startTest();
               
        EntityListController entityController = new EntityListController();
        String testString = 'test1';
        
        test.stopTest();
        
        System.assert(entityController.containsNumbersOrNonAlphabeticCharacters(testString));
    }
    
    static testMethod void addRowTest()
    {
        test.startTest();
        insertTableToDatabase();
        
        EntityListController entityController = new EntityListController();
        
        Integer expectedEntityMapSize = 2;
        
        entityController.addRow(); 
        
        System.assertEquals(expectedEntityMapSize, entityController.entityMap.size());
        
        test.stopTest();
        
    }
    
    static testMethod void getEntitiesTest()
    {
        test.startTest();
              
        insertTableToDatabase();
                
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
        
         EntityListController entityController = new EntityListController();
         entityController.applyChanges();
		 
         System.assertEquals(entityController.entityMap.size(), entityController.entitiesToUpdate.size());
         test.stopTest();
    }
    
   	static testMethod void isAlreadyExistTest()
    {
         test.startTest();
        
         insertCustomSettingObject();

         Entity__c entityTest = insertTableToDatabase();

         EntityListController entityController = new EntityListController();
        
         Set<Entity__c> entitiesToUpdate = new Set<Entity__c>();
        
         entitiesToUpdate.add(entityTest);
             
       	 System.assert(entityController.isAlreadyExist(entitiesToUpdate, entityTest.TableName__c));
        
         test.stopTest();
    }
    
    static testMethod void isGenerateButtonVisibleTest()
    {
        test.startTest();
                             
        System.assert(!EntityListController.isGenerateButtonVisible());
          
       	test.stopTest();
    }
    
    static testMethod void resolveChanges()
    {
         test.startTest();
             
         EntityListController entityController = new EntityListController();
         entityController.resolveChanges();
        
         System.assert(entityController.wereChanges);
        
         test.stopTest();
    }
    
    static testMethod void resetIsProcessingNeededTest()
    {
         test.startTest();
              
         Entity__c entityTest = new Entity__c();
         entityTest.TableName__c = 'entityTableNameTest';
         entityTest.Name = 'entityNameTest';
         entityTest.IsProcessingNeeded__c = true;
        
         Database.insert(entityTest);
         
         EntityUtil.resetIsProcessingNeeded();
        
         Boolean expectedIsProcessingNeededStatus = false;
              
         System.assertEquals(EntityUtil.entityList.get(EntityUtil.entityList.size()-1).IsProcessingNeeded__c, expectedIsProcessingNeededStatus);
        
         test.stopTest();
    }
       
    static testMethod void deleteEntityTriggerTest()
    {
      	test.startTest();
        
     	Entity__c entityDeleteTest = new Entity__c();
      	entityDeleteTest.TableName__c = 'entityTableNameTest';
      	entityDeleteTest.Name = 'entityNameTest';
             
     	Database.insert(entityDeleteTest);  
            
        Database.DeleteResult result = Database.delete(entityDeleteTest);  
        
        System.assert(result.isSuccess());
        
        test.stopTest();
           
    }
    
    static testMethod void getRemoteSettingURLTest()
    {
        test.startTest();
                 
        String expectedUrl = 'testUrl';
        
        System.assertEquals(expectedUrl, EntityListController.getRemoteSettingURL());
             
        test.stopTest();
    }
    
    
       
    public static void insertCustomSettingObject()
    {
        CustomSettingObject__c customSettingObject = new CustomSettingObject__c();
        customSettingObject.Name = 'RemoteSiteURL';
         customSettingObject.RemoteSiteURLField__c = 'testUrl';
        Database.insert(customSettingObject);
    }
    
    private static Entity__c insertTableToDatabase()
    {
         Entity__c entityTest = new Entity__c();
         entityTest.TableName__c = 'entityTableNameTest';
         entityTest.Name = 'entityNameTest';
        
         Database.insert(entityTest);
        
         return entityTest;
    }
}