@isTest
public class CalloutClassTest 
{   
	@isTest 
    static void testCallout() 
     {
        test.startTest();
         
        CustomSettingObject__c customSettingObject = new CustomSettingObject__c();
        customSettingObject.Name = 'RemoteSiteURL';
        customSettingObject.RemoteSiteURLField__c = 'http://23.251.129.95:8080/';
        insert customSettingObject;
         
        Test.setMock(HttpCalloutMock.class, new HttpCalloutMockImpl());
        Integer actualStatus = EntityListController.generateEntities();
        Integer expectedStatus =  200;
        System.assertEquals(expectedStatus, actualStatus);
         
        test.stopTest();
    }
}