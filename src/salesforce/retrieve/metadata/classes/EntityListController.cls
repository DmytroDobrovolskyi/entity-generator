public class EntityListController
{
    public String entityId { get; set; }

    public Boolean wereChanges { get; set; }

    private Map<String, Entity__c> entityMap;

    private Entity__c newEntity;

    public EntityListController()
    {
        this.entityMap = new Map<String, Entity__c>
        ([
            SELECT Id, Name, TableName__c
            FROM Entity__c
        ]);
    }

    public List<Entity__c> getEntities()
    {
        return entityMap.values();
    }

    public void deleteEntity()
    {
        Database.delete(entityId);
        entityMap.remove(entityId);
    }

    public void addRow()
    {
        newEntity = new Entity__c();
        entityMap.put(null, newEntity);
        wereChanges = true;
    }

    public void applyChanges()
    {
        if (newEntity != null)
        {
            Database.insert(newEntity);
            entityMap.remove(null);
            entityMap.put(newEntity.Id, newEntity);
            newEntity = null;
        }
        Database.update(entityMap.values(), false);
        wereChanges = false;
    }

    public PageReference discardChanges() 
    {
        return new PageReference(ApexPages.currentPage().getHeaders().get('referer'));
    }

    public void setWereChanges()
    {
        wereChanges = true;
    }
}