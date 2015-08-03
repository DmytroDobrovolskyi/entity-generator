trigger EntityTrigger on Entity__c (before insert, after insert, after update, after delete)
{
   SObjectDomain.handleTrigger(EntityDomain.class);
}
