@isTest
public class EntityListControllerTest
{
 	static testMethod void containsNumbersOrNonAlphabeticCharactersTest()
    {
        test.startTest();
        
        insertCustomSettingObject();
        
        EntityListController entityController = new EntityListController();
        String testString = 'test1';
        
        test.stopTest();
        
        System.assert(entityController.containsNumbersOrNonAlphabeticCharacters(testString));
    }
    
    static testMethod void getEntitiesTest()
    {
        test.startTest();
        
        insertCustomSettingObject();
        
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
        
        insertCustomSettingObject();

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
        
         insertCustomSettingObject();

         insertTableToDatabase();

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
        
        insertCustomSettingObject();
                      
        System.assert(!EntityListController.isGenerateButtonVisible());
          
       	test.stopTest();
    }
    
    static testMethod void resolveChanges()
    {
         test.startTest();
        
         insertCustomSettingObject();
       
         EntityListController entityController = new EntityListController();
         entityController.resolveChanges();
        
         System.assert(entityController.wereChanges);
        
         test.stopTest();
    }
    
    static testMethod void resetIsProcessingNeededTest()
    {
         test.startTest();
        
         insertCustomSettingObject();
        
         Entity__c entityTest = new Entity__c();
         entityTest.TableName__c = 'entityTableNameTest';
         entityTest.Name = 'entityNameTest';
         entityTest.IsProcessingNeeded__c = true;
        
         Database.insert(entityTest);
         
         EntityUtil.resetIsProcessingNeeded();
        
         Boolean expectedIsProcessingNeededStatus = false;
              
         System.assertEquals( EntityUtil.entityList.get(EntityUtil.entityList.size()-1).IsProcessingNeeded__c, expectedIsProcessingNeededStatus);
        
         test.stopTest();
    }
       
    public static void insertCustomSettingObject()
    {
        CustomSettingObject__c customSettingObject = new CustomSettingObject__c();
        customSettingObject.Name = 'RemoteSiteURL';
        customSettingObject.RemoteSiteURLField__c = '';
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