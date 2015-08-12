@isTest
public class EditEntityControllerTest 
{   
    static testMethod void wereChangesTest()
    {
        test.startTest();
               
        ApexPages.StandardController controller = new ApexPages.StandardController(addEntitiesAndFieldsToTestDatabase());
        EditEntityController editEntityController = new EditEntityController(controller);
		editEntityController.addRow();
                     
        test.stopTest();  
             
        System.assert(editEntityController.wereChanges);
    }
    
    static testMethod void containsNumbersOrNonAlphabeticCharactersTest()
    {       
        test.startTest();
                      
        ApexPages.StandardController controller = new ApexPages.StandardController(addEntitiesAndFieldsToTestDatabase());
        EditEntityController editEntityController = new EditEntityController(controller);
        
        test.stopTest();
        
		String testString = 'te0st';
        
        System.assert(editEntityController.containsNumbersOrNonAlphabeticCharacters(testString));

    }
    
    static testMethod void getFieldsTest()
    {
		test.startTest();
        
        Entity__c entityTest = new Entity__c();
        entityTest.TableName__c = 'entityTableNameTest';
        entityTest.Name = 'entityNameTest';
        
        Database.insert(entityTest);
        
        Field__c firstField = new Field__c();
        firstField.ColumnName__c = 'firstfieldColumnName';
        firstField.Name = 'firstField';
        firstField.Type__c = 'int';
        firstField.IsPrimaryKey__c = false;
        firstField.Entity__c = entityTest.Id;
        
        Field__c secondField = new Field__c();
        secondField.ColumnName__c = 'secondfieldColumnName';
        secondField.Name = 'secondField';
        secondField.Type__c = 'int';
        secondField.IsPrimaryKey__c = false;
        secondField.Entity__c = entityTest.Id;
        
        Database.insert(firstField);
        Database.insert(secondField);

        ApexPages.StandardController controller = new ApexPages.StandardController(entityTest);
        EditEntityController editEntityController = new EditEntityController(controller); 
        
        Integer size = editEntityController.fieldMap.size();        
        Integer expectedFieldsListSize = 2;
        
        test.stopTest();
        
        System.assertEquals(expectedFieldsListSize,editEntityController.getFields().size());
    }
    
    static testMethod void getTypesTest()
    {
        test.startTest();
        
        ApexPages.StandardController controller = new ApexPages.StandardController(addEntitiesAndFieldsToTestDatabase());              
        EditEntityController editEntityController = new EditEntityController(controller);
        Integer expectedSelectedOptionListSize = 6;
        
        test.stopTest();
        
        System.assertEquals(expectedSelectedOptionListSize,editEntityController.getTypes().size());
    }

    static testMethod void applyChangesTest()
    {
        test.startTest();
        Entity__c entityTest = new Entity__c();
        entityTest.TableName__c = 'entityTableNameTest';
        entityTest.Name = 'entityNameTest';

        Database.insert(entityTest);

        Field__c firstField = new Field__c();
        firstField.ColumnName__c = 'firstfieldColumnName';
        firstField.Name = 'firstField';
        firstField.Type__c = 'int';
        firstField.IsPrimaryKey__c = false;
        firstField.Entity__c = entityTest.Id;

        Database.insert(firstField);

        ApexPages.StandardController controller = new ApexPages.StandardController(entityTest);
        EditEntityController editEntityController = new EditEntityController(controller);

        editEntityController.applyChanges();

        test.stopTest();
    }
    
    static testMethod void resolveChanges()
    {
         test.startTest();
        
         ApexPages.StandardController controller = new ApexPages.StandardController(addEntitiesAndFieldsToTestDatabase());
         EditEntityController editEntityController = new EditEntityController(controller);
         editEntityController.resolveChanges();
        
         System.assert(editEntityController.wereChanges);
        
         test.stopTest();
    }
    
    static testMethod void isAlreadyExistTest()
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

         ApexPages.StandardController controller = new ApexPages.StandardController(entityTest);
         EditEntityController editEntityController = new EditEntityController(controller);
        
         Set<Field__c> fieldsToUpdate = new Set<Field__c>();
        
         fieldsToUpdate.add(fieldTest);
             
       	 System.assert(editEntityController.isAlreadyExist(fieldsToUpdate, fieldTest.ColumnName__c));
        
         test.stopTest();
    }
    
    static testMethod void deleteFieldTrigger()
    {
        test.startTest();
        
        Entity__c entityTest = new Entity__c();
        entityTest.TableName__c = 'entityTableNameTest';
        entityTest.Name = 'entityNameTest';

        Database.insert(entityTest);

        Field__c firstField = new Field__c();
        firstField.ColumnName__c = 'firstfieldColumnName';
        firstField.Name = 'firstField';
        firstField.Type__c = 'int';
        firstField.IsPrimaryKey__c = false;
        firstField.Entity__c = entityTest.Id;

        Database.insert(firstField);
                        
        Database.DeleteResult result = Database.delete(firstField);  
        
        System.assert(result.isSuccess());
        
        test.stopTest();
    }
    
    static testMethod void isProcessingNeededTest()
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

        ApexPages.StandardController controller = new ApexPages.StandardController(entityTest);
        EditEntityController editEntityController = new EditEntityController(controller);
          
        editEntityController.setIsProcessingNeeded();
        
        System.assert(editEntityController.entity.IsProcessingNeeded__c);
        
        test.stopTest();
        
    }
    
    static testMethod void resolveIsPrimaryKeyTest()
    {
        test.startTest();
		Entity__c entityTest = new Entity__c();
        entityTest.TableName__c = 'entityTableNameTest';
        entityTest.Name = 'entityNameTest';
        
        Database.insert(entityTest);
        
        Field__c fieldTest = new Field__c();
        fieldTest.ColumnName__c = 'fieldColumnName';
        fieldTest.Name = 'fieldTest';
        fieldTest.Type__c = 'date';
        fieldTest.IsPrimaryKey__c = true;
        
        ApexPages.StandardController controller = new ApexPages.StandardController(entityTest);
        EditEntityController editEntityController = new EditEntityController(controller);
         
        editEntityController.resolveIsPrimaryKey(fieldTest);
      
        System.assert(!editEntityController.newField.IsPrimaryKey__c);
        
        test.stopTest();
    }
      	
    public static Entity__c addEntitiesAndFieldsToTestDatabase()
    {
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
        
        return entityTest;
    }
}