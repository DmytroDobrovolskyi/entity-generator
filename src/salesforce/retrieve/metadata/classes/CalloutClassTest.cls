@isTest
public class CalloutClassTest 
{
    static 
    {
        CustomSettingObject__c customSettingObject = new CustomSettingObject__c();
        customSettingObject.Name = 'RemoteSiteURL';
        customSettingObject.RemoteSiteURLField__c = 'http://23.251.129.95:8080/';
        insert customSettingObject;
    }
    
	@isTest 
    static void testCallout() 
     {
        Test.setMock(HttpCalloutMock.class, new HttpCalloutMockImpl());
        Integer res = EntityListController.generateEntities();
         
        System.assertEquals(200, res);
    }
}