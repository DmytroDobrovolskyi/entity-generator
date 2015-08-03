trigger FieldTrigger on Field__c (before insert, after insert, after update, after delete) 
{
    SObjectDomain.handleTrigger(FieldDomain.class);
}
