@isTest
public class CalloutClassTest 
{   
    static 
    {
        CustomSettingObject__c customSettingObject = new CustomSettingObject__c();
        customSettingObject.Name = 'RemoteSiteURL';
        
        insert customSettingObject;
    }
    
	@isTest 
    static void testCallout() 
     {
        test.startTest();
             
        Test.setMock(HttpCalloutMock.class, new HttpCalloutMockImpl());
         
        Integer actualStatus = EntityListController.generateEntities();
        Integer expectedStatus =  200;
        System.assertEquals(expectedStatus, actualStatus);
         
        test.stopTest();
    }
}