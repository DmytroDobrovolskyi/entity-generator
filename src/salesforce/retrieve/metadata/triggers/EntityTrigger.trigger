trigger EntityTrigger on Entity__c (before insert) 
{
   SObjectDomain.handleTrigger(EntityDomain.class);
}